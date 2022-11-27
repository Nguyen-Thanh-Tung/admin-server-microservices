package com.comit.services.location.business;

import com.comit.services.location.client.data.OrganizationDtoClient;
import com.comit.services.location.constant.Const;
import com.comit.services.location.constant.LocationErrorCode;
import com.comit.services.location.controller.request.LocationRequest;
import com.comit.services.location.exception.RestApiException;
import com.comit.services.location.loging.model.CommonLogger;
import com.comit.services.location.middleware.LocationVerifyRequestServices;
import com.comit.services.location.model.dto.BaseLocationDto;
import com.comit.services.location.model.dto.LocationDto;
import com.comit.services.location.model.entity.Location;
import com.comit.services.location.service.LocationServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private HttpServletRequest httpServletRequest;
    @Value("${app.internalToken}")
    private String internalToken;

    @Override
    public Page<Location> getLocationPage(int page, int size, String search) {
        Pageable paging = PageRequest.of(page, size);
        OrganizationDtoClient organizationDtoClient = locationServices.getOrganizationOfCurrentUser();
        if (locationServices.hasPermissionManagerLocation(Const.TIME_KEEPING_TYPE) && locationServices.isTimeKeepingModule()) {
            return locationServices.getLocationPage(organizationDtoClient.getId(), Const.TIME_KEEPING_TYPE, search, paging);
        } else if (locationServices.hasPermissionManagerLocation(Const.AREA_RESTRICTION_TYPE) && locationServices.isAreaRestrictionModule()) {
            return locationServices.getLocationPage(organizationDtoClient.getId(), Const.AREA_RESTRICTION_TYPE, search, paging);
        } else if (locationServices.hasPermissionManagerLocation(Const.BEHAVIOR_TYPE) && locationServices.isBehaviorModule()) {
            return locationServices.getLocationPage(organizationDtoClient.getId(), Const.BEHAVIOR_TYPE, search, paging);
        } else {
            return null;
        }
    }

    @Override
    public List<LocationDto> getAllLocation(List<Location> locations) {
        List<LocationDto> locationDtos = new ArrayList<>();
        locations.forEach(location -> {
            locationDtos.add(convertLocationToLocationDto(location));
        });
        return locationDtos;
    }

    @Override
    public LocationDto addLocation(LocationRequest request) {
        verifyRequestServices.verifyAddOrUpdateLocationRequest(request);
        OrganizationDtoClient organizationDtoClient = locationServices.getOrganizationOfCurrentUser();
        if (!locationServices.hasPermissionManagerLocation(request.getType())) {
            throw new RestApiException(LocationErrorCode.PERMISSION_DENIED);
        }

        if (organizationDtoClient == null) {
            throw new RestApiException(LocationErrorCode.ORGANIZATION_NOT_EXIST);
        }
        if (locationServices.existLocation(request.getCode(), organizationDtoClient.getId())) {
            throw new RestApiException(LocationErrorCode.LOCATION_CODE_EXIST);
        }

        Location location = new Location();
        location.setName(request.getName());
        location.setCode(request.getCode());
        location.setOrganizationId(organizationDtoClient.getId());
        location.setType(request.getType());
        Location newLocation = locationServices.saveLocation(location);


        if (Objects.equals(newLocation.getType(), Const.TIME_KEEPING_TYPE)) {
            //Add shift for location (time keeping module)
            locationServices.addShiftsForLocation(newLocation.getId());

            //Add time keeping notification setting for location (time keeping module)
            locationServices.addTimeKeepingNotification(newLocation.getId());
        } else if (Objects.equals(newLocation.getType(), Const.AREA_RESTRICTION_TYPE)) {

        }
        return convertLocationToLocationDto(newLocation);
    }

    @Override
    public LocationDto updateLocation(int locationId, LocationRequest request) {
        verifyRequestServices.verifyAddOrUpdateLocationRequest(request);
        OrganizationDtoClient organizationDtoClient = locationServices.getOrganizationOfCurrentUser();
        Location location = locationServices.getLocation(organizationDtoClient.getId(), locationId);
        if (location == null) {
            throw new RestApiException(LocationErrorCode.LOCATION_NOT_EXIST);
        }

        if (!locationServices.hasPermissionManagerLocation(request.getType())) {
            throw new RestApiException(LocationErrorCode.PERMISSION_DENIED);
        }

        if (!Objects.equals(location.getCode(), request.getCode()) && locationServices.existLocation(request.getCode(), organizationDtoClient.getId())) {
            throw new RestApiException(LocationErrorCode.LOCATION_CODE_EXIST);
        }
        location.setName(request.getName());
        location.setCode(request.getCode());
        location.setOrganizationId(organizationDtoClient.getId());
        Location newLocation = locationServices.saveLocation(location);
        return convertLocationToLocationDto(newLocation);
    }

    @Override
    public boolean deleteLocation(int id) {
        OrganizationDtoClient organizationDtoClient = locationServices.getOrganizationOfCurrentUser();
        Location location = locationServices.getLocation(organizationDtoClient.getId(), id);
        if (location == null) {
            throw new RestApiException(LocationErrorCode.LOCATION_NOT_EXIST);
        }
        if (!locationServices.hasPermissionManagerLocation(location.getType())) {
            throw new RestApiException(LocationErrorCode.PERMISSION_DENIED);
        }

        int numberEmployeeOfLocation = locationServices.getNumberEmployeeOfLocation(location.getId());
        int numberCameraOfLocation = locationServices.getNumberCameraOfLocation(location.getId());
        int numberUserOfLocation = locationServices.getNumberUserOfLocation(location.getId());
        if (numberCameraOfLocation != 0 || numberEmployeeOfLocation != 0 || numberUserOfLocation != 0) {
            return false;
        }

        // Shift and time keeping notification is auto create for time keeping
        locationServices.deleteShiftsOfLocation(location.getId());
        locationServices.deleteTimeKeepingNotification(location.getId());
        return locationServices.deleteLocation(id);
    }

    @Override
    public LocationDto getLocation(int id) {
        Location location = locationServices.getLocation(id);
        if (location == null) {
            throw new RestApiException(LocationErrorCode.LOCATION_NOT_EXIST);
        }

        return convertLocationToLocationDto(location);
    }

    @Override
    public BaseLocationDto getLocationBase(int id) {
        // For internal request
        if (!isInternalFeature()) throw new RestApiException(LocationErrorCode.PERMISSION_DENIED);

        Location location = locationServices.getLocation(id);
        if (location == null) {
            throw new RestApiException(LocationErrorCode.LOCATION_NOT_EXIST);
        }

        return convertLocationToBaseLocationDto(location);
    }

    @Override
    public List<BaseLocationDto> getAllLocationByOrganizationId(int organizationId, String type) {
        if (!isInternalFeature()) throw new RestApiException(LocationErrorCode.PERMISSION_DENIED);
        List<Location> locations = locationServices.getAllLocationByOrganizationId(organizationId, type);
        List<BaseLocationDto> locationDtos = new ArrayList<>();
        locations.forEach(location -> {
            locationDtos.add(convertLocationToBaseLocationDto(location));
        });
        return locationDtos;
    }

    public BaseLocationDto convertLocationToBaseLocationDto(Location location) {
        if (location == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(location, BaseLocationDto.class);
        } catch (Exception e) {
            CommonLogger.error(e.getMessage(), e);
            return null;
        }
    }
    public LocationDto convertLocationToLocationDto(Location location) {
        if (location == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            LocationDto locationDto = modelMapper.map(location, LocationDto.class);
            int numberEmployeeOfLocation = locationServices.getNumberEmployeeOfLocation(location.getId());
            int numberCameraOfLocation = locationServices.getNumberCameraOfLocation(location.getId());
            locationDto.setNumberCamera(numberCameraOfLocation);
            locationDto.setNumberEmployees(numberEmployeeOfLocation);
            return locationDto;
        } catch (Exception e) {
            CommonLogger.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean isInternalFeature() {
        return Objects.equals(httpServletRequest.getHeader("token"), internalToken);
    }
}
