package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.client.AccountClient;
import com.comit.services.areaRestriction.client.CameraClient;
import com.comit.services.areaRestriction.client.HistoryClient;
import com.comit.services.areaRestriction.client.LocationClient;
import com.comit.services.areaRestriction.client.data.EmployeeDtoClient;
import com.comit.services.areaRestriction.client.data.LocationDtoClient;
import com.comit.services.areaRestriction.client.response.CountCameraResponseClient;
import com.comit.services.areaRestriction.client.response.EmployeeResponseClient;
import com.comit.services.areaRestriction.client.response.LocationResponseClient;
import com.comit.services.areaRestriction.client.response.UserResponseClient;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.comit.services.areaRestriction.repository.AreaRestrictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
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
    public AreaRestriction getAreaRestriction(Integer locationId, String name) {
        return areaRestrictionRepository.findByLocationIdAndName(locationId, name);
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
            LocationResponseClient locationResponseClient = locationClient.getLocationById(httpServletRequest.getHeader("token"), userResponse.getUser().getLocationId()).getBody();
            if (locationResponseClient == null) {
                throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
            }
            return locationResponseClient.getLocation();
        }
        return null;
    }

    @Override
    public int getNumberNotificationOfAreaRestriction(AreaRestriction areaRestriction, Date startDay, Date toDate) {
        CountCameraResponseClient countCameraResponseClient = historyClient.getNumberNotificationOfAreaRestriction(httpServletRequest.getHeader("token"), areaRestriction.getId()).getBody();
        if (countCameraResponseClient == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return countCameraResponseClient.getNumber();
    }

    @Override
    public EmployeeDtoClient getEmployee(Integer managerId) {
        EmployeeResponseClient employeeResponseClient = employeeClient.getEmployee(httpServletRequest.getHeader("token"), managerId).getBody();
        if (employeeResponseClient == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return employeeResponseClient.getEmployee();
    }

    @Override
    public int getNumberCameraOfAreaRestriction(int areaRestrictionId) {
        CountCameraResponseClient countCameraResponseClient = cameraClient.getNumberCameraOfAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId).getBody();
        if (countCameraResponseClient == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return countCameraResponseClient.getNumber();
    }

    @Override
    public List<AreaRestriction> getAllAreaRestrictionOfManager(Integer managerId) {
        return areaRestrictionRepository.findByManagerIdsOrManagerIdsContaining(managerId.toString(), managerId + ",");
    }
}
