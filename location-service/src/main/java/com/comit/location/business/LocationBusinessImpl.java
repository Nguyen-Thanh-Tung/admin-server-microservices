package com.comit.location.business;

import com.comit.location.client.*;
import com.comit.location.client.response.*;
import com.comit.location.constant.Const;
import com.comit.location.constant.LocationErrorCode;
import com.comit.location.controller.request.LocationRequest;
import com.comit.location.controller.response.BaseResponse;
import com.comit.location.exception.RestApiException;
import com.comit.location.middleware.LocationVerifyRequestServices;
import com.comit.location.model.dto.LocationDto;
import com.comit.location.model.entity.*;
import com.comit.location.service.LocationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LocationBusinessImpl implements LocationBusiness {
    @Autowired
    private LocationVerifyRequestServices verifyRequestServices;
    @Autowired
    private LocationServices locationServices;

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private ShiftClient shiftClient;
    @Autowired
    private TimeKeepingNotificationClient timeKeepingNotificationClient;
    @Autowired
    private OrganizationClient organizationClient;
    @Autowired
    private EmployeeClient employeeClient;
    @Autowired
    private CameraClient cameraClient;

    @Override
    public Page<Location> getLocationPage(int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);
        Organization organization = getOrganizationOfCurrentUser();
        CheckRoleResponse checkRoleTKResponse = accountClient.hasPermissionManagerLocation(Const.TIME_KEEPING_TYPE).getBody();
        CheckRoleResponse checkRoleARResponse = accountClient.hasPermissionManagerLocation(Const.AREA_RESTRICTION_TYPE).getBody();
        CheckModuleResponse checkModuleTKResponse = accountClient.isTimeKeepingModule().getBody();
        CheckModuleResponse checkModuleARResponse = accountClient.isAreaRestrictionModule().getBody();

        if (checkRoleTKResponse == null || checkRoleARResponse == null || checkModuleARResponse == null || checkModuleTKResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
        if (checkRoleTKResponse.getHasRole() && checkModuleTKResponse.getIsModule()) {
            return locationServices.getLocationPage(organization.getId(), Const.TIME_KEEPING_TYPE, search, paging);
        } else if (checkRoleARResponse.getHasRole() && checkModuleARResponse.getIsModule()) {
            return locationServices.getLocationPage(organization.getId(), Const.AREA_RESTRICTION_TYPE, search, paging);
        }

        return null;
    }

    @Override
    public List<LocationDto> getAllLocation(List<Location> locations) {
        List<LocationDto> locationDtos = new ArrayList<>();
        locations.forEach(location -> {
            locationDtos.add(LocationDto.convertLocationToLocationDto(location));
        });
        return locationDtos;
    }

    @Override
    public LocationDto addLocation(LocationRequest request) {
        verifyRequestServices.verifyAddOrUpdateLocationRequest(request);
        Organization organization = getOrganizationOfCurrentUser();
        checkRoleManagerLocation(request.getType());

        if (organization == null) {
            throw new RestApiException(LocationErrorCode.ORGANIZATION_NOT_EXIST);
        }
        if (locationServices.existLocation(request.getCode(), organization.getId())) {
            throw new RestApiException(LocationErrorCode.LOCATION_CODE_EXIST);
        }

        Location location = new Location();
        location.setName(request.getName());
        location.setCode(request.getCode());
        location.setOrganizationId(organization.getId());
        location.setType(request.getType());
        Location newLocation = locationServices.saveLocation(location);


        if (Objects.equals(newLocation.getType(), Const.TIME_KEEPING_TYPE)) {
            //Add shift for location (time keeping module)
            BaseResponse response = shiftClient.addShiftsForLocation(newLocation.getId()).getBody();
            if (response == null || response.getCode() != LocationErrorCode.SUCCESS.getCode()) {
                throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
            }

            //Add time keeping notification setting for location (time keeping module)
            BaseResponse tkResponse = timeKeepingNotificationClient.addTimeKeepingNotification(newLocation.getId()).getBody();
            if (tkResponse == null || tkResponse.getCode() != LocationErrorCode.SUCCESS.getCode()) {
                throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
            }
        } else if (Objects.equals(newLocation.getType(), Const.AREA_RESTRICTION_TYPE)) {

        }
        return LocationDto.convertLocationToLocationDto(newLocation);
    }

    private void checkRoleManagerLocation(String type) {
        CheckRoleResponse checkRoleResponse = accountClient.hasPermissionManagerLocation(type).getBody();
        if (checkRoleResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
        if (!checkRoleResponse.getHasRole()) {
            throw new RestApiException(LocationErrorCode.PERMISSION_DENIED);
        }
    }

    @Override
    public LocationDto updateLocation(int locationId, LocationRequest request) {
        verifyRequestServices.verifyAddOrUpdateLocationRequest(request);
        Organization organization = getOrganizationOfCurrentUser();

        if (organization == null) {
            throw new RestApiException(LocationErrorCode.ORGANIZATION_NOT_EXIST);
        }

        Location location = locationServices.getLocation(organization.getId(), locationId);
        if (location == null) {
            throw new RestApiException(LocationErrorCode.LOCATION_NOT_EXIST);
        }

        checkRoleManagerLocation(location.getType());

        if (!Objects.equals(location.getCode(), request.getCode()) && locationServices.existLocation(request.getCode(), organization.getId())) {
            throw new RestApiException(LocationErrorCode.LOCATION_CODE_EXIST);
        }
        location.setName(request.getName());
        location.setCode(request.getCode());
        location.setOrganizationId(organization.getId());
        Location newLocation = locationServices.saveLocation(location);
        return LocationDto.convertLocationToLocationDto(newLocation);
    }

    @Override
    public boolean deleteLocation(int id) {
        Organization organization = getOrganizationOfCurrentUser();
        Location location = locationServices.getLocation(organization.getId(), id);
        if (location == null) {
            throw new RestApiException(LocationErrorCode.LOCATION_NOT_EXIST);
        }
        checkRoleManagerLocation(location.getType());

        EmployeeListResponse employeeListResponse = employeeClient.getEmployeeOfLocation(location.getId()).getBody();
        CameraListResponse cameraListResponse = cameraClient.getCameraOfLocation(location.getId()).getBody();
        UserListResponse userListResponse = accountClient.getUserOfLocation(location.getId()).getBody();
        if (employeeListResponse == null || cameraListResponse == null || userListResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
        if (userListResponse.getUsers().size() != 0 ||
                employeeListResponse.getEmployees().size() != 0 ||
                cameraListResponse.getCameras().size() != 0) {
            return false;
        }

        // Shift and time keeping notification is auto create for time keeping
        BaseResponse shiftResponse = shiftClient.deleteShiftsOfLocation(location.getId()).getBody();
        if (shiftResponse == null || shiftResponse.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
        BaseResponse tkResponse = timeKeepingNotificationClient.deleteTimeKeepingNotification(location.getId()).getBody();
        if (tkResponse == null || tkResponse.getCode() != LocationErrorCode.SUCCESS.getCode()) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }
        return locationServices.deleteLocation(id);
    }

    @Override
    public LocationDto getLocation(int id) {
        Organization organization = getOrganizationOfCurrentUser();
        Location location = locationServices.getLocation(organization.getId(), id);
        if (location == null) {
            throw new RestApiException(LocationErrorCode.LOCATION_NOT_EXIST);
        }
        checkRoleManagerLocation(location.getType());

        return LocationDto.convertLocationToLocationDto(location);
    }

    @Override
    public List<LocationDto> getAllLocationByOrganizationId(int organizationId) {
        List<Location> locations = locationServices.getAllLocationByOrganizationId(organizationId);
        List<LocationDto> locationDtos = new ArrayList<>();
        locations.forEach(location -> {
            locationDtos.add(LocationDto.convertLocationToLocationDto(location));
        });
        return locationDtos;
    }

    private Organization getOrganizationOfCurrentUser() {
        OrganizationResponse organizationResponse = organizationClient.getOrganizationOfCurrentUser().getBody();
        if (organizationResponse == null) {
            throw new RestApiException(LocationErrorCode.INTERNAL_ERROR);
        }

        return organizationResponse.getOrganization();
    }
}
