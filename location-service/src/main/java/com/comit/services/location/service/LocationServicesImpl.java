package com.comit.services.location.service;
import com.comit.services.location.client.*;
import com.comit.services.location.client.response.*;
import com.comit.services.location.constant.Const;
import com.comit.services.location.constant.LocationErrorCode;
import com.comit.services.location.controller.response.BaseResponse;
import com.comit.services.location.exception.RestApiException;
import com.comit.services.location.model.entity.*;
import com.comit.services.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
public class LocationServicesImpl implements LocationServices {
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private TimeKeepingClient timeKeepingClient;
    @Autowired
    private EmployeeClient employeeClient;
    @Autowired
    private CameraClient cameraClient;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Location getLocation(Integer organizationId, int locationId) {
        return locationRepository.findByIdAndOrganizationId(locationId, organizationId);
    }

    @Override
    public Location getLocation(int locationId) {
        return locationRepository.findById(locationId);
    }

    @Override
    public Page<Location> getLocationPage(Integer organizationId, String type, String search, Pageable paging) {
        if (search != null && !search.trim().isEmpty()) {
            return locationRepository.findByOrganizationIdAndTypeAndNameContainingOrOrganizationIdAndTypeAndCodeContainingOrderByIdDesc(organizationId, type, search, organizationId, type, search, paging);
        }
        return locationRepository.findByOrganizationIdAndTypeOrderByIdDesc(organizationId, type, paging);
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public boolean deleteLocation(int id) {
        try {
            locationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean existLocation(String code, Integer organizationId) {
        return locationRepository.existsByCodeAndOrganizationId(code, organizationId);
    }

    @Override
    public List<Location> getAllLocationByOrganizationId(int organizationId) {
        return locationRepository.findAllByOrganizationId(organizationId);
    }

    @Override
    public Organization getOrganizationOfCurrentUser() {
        OrganizationResponse organizationResponse = accountClient.getOrganizationOfCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (organizationResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }

        return organizationResponse.getOrganization();
    }

    @Override
    public boolean hasPermissionManagerLocation(String type) {
        CheckRoleResponse checkRoleResponse = accountClient.hasPermissionManagerLocation(httpServletRequest.getHeader("token"), type).getBody();
        if (checkRoleResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
        return checkRoleResponse.getHasRole();
    }

    @Override
    public boolean isTimeKeepingModule() {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        return Objects.equals(moduleName, Const.TIME_KEEPING_HEADER_MODULE);
    }

    @Override
    public boolean isAreaRestrictionModule() {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        return Objects.equals(moduleName, Const.AREA_RESTRICTION_HEADER_MODULE);
    }

    @Override
    public boolean isBehaviorModule() {
        String moduleName = httpServletRequest.getHeader(Const.HEADER_MODULE);
        return Objects.equals(moduleName, Const.BEHAVIOR_HEADER_MODULE);
    }

    @Override
    public void addShiftsForLocation(int locationId) {
        BaseResponse response = timeKeepingClient.addShiftsForLocation(httpServletRequest.getHeader("token"), locationId).getBody();
        if (response == null || response.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public void addTimeKeepingNotification(int locationId) {
        BaseResponse tkResponse = timeKeepingClient.addTimeKeepingNotification(httpServletRequest.getHeader("token"), locationId).getBody();
        if (tkResponse == null || tkResponse.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public List<Employee> getEmployeeOfLocation(int locationId) {
        EmployeeListResponse employeeListResponse = employeeClient.getEmployeeOfLocation(httpServletRequest.getHeader("token"), locationId).getBody();
        if (employeeListResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }

        return employeeListResponse.getEmployees();
    }

    @Override
    public List<Camera> getCameraOfLocation(int locationId) {
        CameraListResponse cameraListResponse = cameraClient.getCameraOfLocation(httpServletRequest.getHeader("token"), locationId).getBody();
        if (cameraListResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }

        return cameraListResponse.getCameras();
    }

    @Override
    public List<User> getUserOfLocation(int locationId) {
        UserListResponse userListResponse = accountClient.getUserOfLocation(httpServletRequest.getHeader("token"), locationId).getBody();
        if (userListResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }

        return userListResponse.getUsers();
    }

    @Override
    public void deleteShiftsOfLocation(int locationId) {
        BaseResponse shiftResponse = timeKeepingClient.deleteShiftsOfLocation(httpServletRequest.getHeader("token"), locationId).getBody();
        if (shiftResponse == null || shiftResponse.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public void deleteTimeKeepingNotification(int locationId) {
        BaseResponse tkResponse = timeKeepingClient.deleteTimeKeepingNotification(httpServletRequest.getHeader("token"), locationId).getBody();
        if (tkResponse == null || tkResponse.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
    }
}
