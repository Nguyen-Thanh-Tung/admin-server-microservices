package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.client.AccountClient;
import com.comit.services.areaRestriction.client.CameraClient;
import com.comit.services.areaRestriction.client.HistoryClient;
import com.comit.services.areaRestriction.client.LocationClient;
import com.comit.services.areaRestriction.client.data.EmployeeDtoClient;
import com.comit.services.areaRestriction.client.data.LocationDtoClient;
import com.comit.services.areaRestriction.client.response.*;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.constant.Const;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.loging.model.CommonLogger;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.comit.services.areaRestriction.repository.AreaRestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AreaRestrictionServicesImpl implements AreaRestrictionServices {
    @Autowired
    private AreaRestrictionRepository areaRestrictionRepository;
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private com.comit.services.areaRestriction.client.EmployeeClient employeeClient;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private CameraClient cameraClient;
    @Autowired
    private HistoryClient historyClient;
    @Autowired
    private LocationClient locationClient;
    @Value("${app.internalToken}")
    private String internalToken;

    @Override
    public Page<AreaRestriction> getAreaRestrictionPage(Integer locationId, String search, Pageable paging) {
        if (search != null && !search.trim().isEmpty()) {
            return areaRestrictionRepository.findByLocationIdAndNameContainingOrLocationIdAndCodeContainingOrderByUpdateAtDesc(locationId, search, locationId, search, paging);
        }
        return areaRestrictionRepository.findByLocationIdOrderByUpdateAtDesc(locationId, paging);
    }

    @Override
    public AreaRestriction getAreaRestriction(Integer locationId, Integer id) {
        return areaRestrictionRepository.findByLocationIdAndId(locationId, id);
    }

    @Override
    public AreaRestriction getAreaRestriction(int id) {
        return areaRestrictionRepository.findById(id);
    }

    @Override
    public AreaRestriction getAreaRestriction(Integer locationId, String code) {
        return areaRestrictionRepository.findByLocationIdAndCode(locationId, code);
    }

    @Override
    public AreaRestriction updateAreaRestriction(AreaRestriction areaRestriction) {
        return areaRestrictionRepository.save(areaRestriction);
    }

    @Override
    public boolean deleteAreaRestriction(AreaRestriction areaRestriction) {
        try {
            areaRestrictionRepository.delete(areaRestriction);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public LocationDtoClient getLocationOfCurrentUser() {
        UserResponseClient userResponse = accountClient.getCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (userResponse != null && userResponse.getUser() != null) {
            if (hasRole(Const.ROLE_BEHAVIOR_CONTROL_USER) || hasRole(Const.ROLE_AREA_RESTRICTION_CONTROL_USER)) {
                LocationResponseClient locationResponseClient = locationClient.getLocationById(internalToken, userResponse.getUser().getLocationId()).getBody();
                if (locationResponseClient == null) {
                    CommonLogger.error(AreaRestrictionErrorCode.INTERNAL_ERROR.getMessage() + ": getLocationOfCurrentUser() " +
                            "- Location response client is null");
                    throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
                }
                return locationResponseClient.getLocation();
            }
            CommonLogger.error(AreaRestrictionErrorCode.PERMISSION_DENIED.getMessage() + ": getLocationOfCurrentUser() " +
                    "- currentUser is cadres area restriction or cadres behavior, currentUserId: " + userResponse.getUser().getId());
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.PERMISSION_DENIED);
        }
        CommonLogger.error(AreaRestrictionErrorCode.INTERNAL_ERROR.getMessage() + ": getLocationOfCurrentUser() " +
                "- User response is null");
        throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
    }

    @Override
    public int getNumberNotificationOfAreaRestriction(AreaRestriction areaRestriction, Date startDay, Date toDate) {
        CountCameraResponseClient countCameraResponseClient = historyClient.getNumberNotificationOfAreaRestriction(internalToken, areaRestriction.getId()).getBody();
        if (countCameraResponseClient == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return countCameraResponseClient.getNumber();
    }

    @Override
    public EmployeeDtoClient getEmployee(Integer managerId) {
        EmployeeResponseClient employeeResponseClient = employeeClient.getEmployee(internalToken, managerId).getBody();
        if (employeeResponseClient == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return employeeResponseClient.getEmployee();
    }

    @Override
    public int getNumberCameraOfAreaRestriction(int areaRestrictionId) {
        CountCameraResponseClient countCameraResponseClient = cameraClient.getNumberCameraOfAreaRestriction(internalToken, areaRestrictionId).getBody();
        if (countCameraResponseClient == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return countCameraResponseClient.getNumber();
    }

    @Override
    public List<AreaRestriction> getAllAreaRestrictionOfManager(Integer managerId) {
        return areaRestrictionRepository.findByManagerIdsOrManagerIdsContaining(managerId.toString(), managerId + ",");
    }

    @Override
    public boolean isExistAreaRestriction(Integer locationId, Integer areaRestrictionId) {
        return areaRestrictionRepository.existsAreaRestrictionByLocationIdAndId(locationId, areaRestrictionId);
    }

    @Override
    public boolean hasRole(String roleNeedCheck) {
        CheckRoleResponseClient checkRoleResponseClient = accountClient.hasRole(httpServletRequest.getHeader("token"), roleNeedCheck).getBody();
        if (checkRoleResponseClient != null) {
            return checkRoleResponseClient.getHasRole();
        }
        return false;
    }

    @Override
    public LocationDtoClient getLocationById(Integer locationId) {
        LocationResponseClient locationResponseClient = locationClient.getLocationById(internalToken, locationId).getBody();
        if (locationResponseClient == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return locationResponseClient.getLocation();
    }

}
