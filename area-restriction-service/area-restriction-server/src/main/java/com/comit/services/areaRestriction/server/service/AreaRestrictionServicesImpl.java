package com.comit.services.areaRestriction.server.service;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.response.UserResponse;
import com.comit.services.areaRestriction.server.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.server.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.server.model.AreaRestriction;
import com.comit.services.areaRestriction.server.repository.AreaRestrictionRepository;
import com.comit.services.camera.client.CameraClient;
import com.comit.services.camera.client.response.CountCameraResponse;
import com.comit.services.employee.client.EmployeeClient;
import com.comit.services.employee.client.dto.EmployeeDto;
import com.comit.services.employee.client.response.EmployeeResponse;
import com.comit.services.history.client.HistoryClient;
import com.comit.services.history.client.response.CountNotificationResponse;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.location.client.response.LocationResponse;
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
    private EmployeeClient employeeClient;
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
    public LocationDto getLocationOfCurrentUser() {
        UserResponse userResponse = accountClient.getCurrentUser(httpServletRequest.getHeader("token"));
        LocationResponse locationResponse = locationClient.getLocationById(httpServletRequest.getHeader("token"), userResponse.getUserDto().getLocationId());
        if (locationResponse == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }

    @Override
    public int getNumberNotificationOfAreaRestriction(AreaRestriction areaRestriction, Date startDay, Date toDate) {
        CountNotificationResponse countResponse = historyClient.getNumberNotificationOfAreaRestriction(httpServletRequest.getHeader("token"), areaRestriction.getId());
        if (countResponse == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return countResponse.getNumber();
    }

    @Override
    public EmployeeDto getEmployee(Integer managerId, Integer locationId) {
        EmployeeResponse employeeResponse = employeeClient.getEmployee(httpServletRequest.getHeader("token"), managerId);
        if (employeeResponse == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return Objects.equals(employeeResponse.getEmployee().getLocationId(), locationId) ? employeeResponse.getEmployee() : null;
    }

    @Override
    public int getNumberCameraOfAreaRestriction(int areaRestrictionId) {
        CountCameraResponse countResponse = cameraClient.getNumberCameraOfAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId);
        if (countResponse == null) {
            throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.INTERNAL_ERROR);
        }
        return countResponse.getNumber();
    }

    @Override
    public List<AreaRestriction> getAllAreaRestrictionOfManager(Integer managerId) {
        return areaRestrictionRepository.findByManagerIdsOrManagerIdsContaining(managerId.toString(), managerId + ",");
    }
}
