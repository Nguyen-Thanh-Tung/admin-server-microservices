package com.comit.services.location.server.service;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.response.CheckRoleResponse;
import com.comit.services.account.client.response.CountUserResponse;
import com.comit.services.account.client.response.UserResponse;
import com.comit.services.camera.client.CameraClient;
import com.comit.services.camera.client.response.CountCameraResponse;
import com.comit.services.employee.client.EmployeeClient;
import com.comit.services.employee.client.response.CountEmployeeResponse;
import com.comit.services.location.server.constant.Const;
import com.comit.services.location.server.constant.LocationErrorCode;
import com.comit.services.location.server.exception.RestApiException;
import com.comit.services.location.server.model.Location;
import com.comit.services.location.server.repository.LocationRepository;
import com.comit.services.organization.client.OrganizationClient;
import com.comit.services.organization.client.dto.OrganizationDto;
import com.comit.services.organization.client.response.OrganizationResponse;
import com.comit.services.timeKeeping.client.TimeKeepingClient;
import com.comit.services.timeKeeping.client.response.BaseResponse;
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
    @Autowired
    private OrganizationClient organizationClient;

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
    public OrganizationDto getOrganizationOfCurrentUser() {
        UserResponse userResponse = accountClient.getCurrentUser(httpServletRequest.getHeader("token"));
        OrganizationResponse organizationResponse = organizationClient.getOrganization(httpServletRequest.getHeader("token"), userResponse.getUserDto().getOrganizationId());
        if (organizationResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }

        return organizationResponse.getOrganization();
    }

    @Override
    public boolean hasPermissionManagerLocation(String type) {
        CheckRoleResponse checkRoleResponse = accountClient.hasPermissionManagerLocation(httpServletRequest.getHeader("token"), type);
        if (checkRoleResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
        return checkRoleResponse.isHasRole();
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
        BaseResponse response = timeKeepingClient.addShiftsForLocation(httpServletRequest.getHeader("token"), locationId);
        if (response == null || response.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public void addTimeKeepingNotification(int locationId) {
        BaseResponse tkResponse = timeKeepingClient.addTimeKeepingNotification(httpServletRequest.getHeader("token"), locationId);
        if (tkResponse == null || tkResponse.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public int getNumberEmployeeOfLocation(int locationId) {
        CountEmployeeResponse countResponse = employeeClient.getNumberEmployeeOfLocation(httpServletRequest.getHeader("token"), locationId);
        if (countResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }

        return countResponse.getNumber();
    }

    @Override
    public int getNumberCameraOfLocation(int locationId) {
        CountCameraResponse countResponse = cameraClient.getNumberCameraOfLocation(httpServletRequest.getHeader("token"), locationId);
        if (countResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }

        return countResponse.getNumber();
    }

    @Override
    public int getNumberUserOfLocation(int locationId) {
        CountUserResponse countResponse = accountClient.getNumberUserOfLocation(httpServletRequest.getHeader("token"), locationId);
        if (countResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }

        return countResponse.getNumber();
    }

    @Override
    public void deleteShiftsOfLocation(int locationId) {
        BaseResponse shiftResponse = timeKeepingClient.deleteShiftsOfLocation(httpServletRequest.getHeader("token"), locationId);
        if (shiftResponse == null || shiftResponse.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
    }

    @Override
    public void deleteTimeKeepingNotification(int locationId) {
        BaseResponse tkResponse = timeKeepingClient.deleteTimeKeepingNotification(httpServletRequest.getHeader("token"), locationId);
        if (tkResponse == null || tkResponse.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
    }
}
