package com.comit.services.location.business;

import com.comit.services.location.controller.request.LocationRequest;
import com.comit.services.location.model.dto.BaseLocationDto;
import com.comit.services.location.model.dto.LocationDto;
import com.comit.services.location.model.entity.Location;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LocationBusiness {
    Page<Location> getLocationPage(int page, int size, String search);

    List<LocationDto> getAllLocation(List<Location> content);

    LocationDto addLocation(LocationRequest addLocationRequest);

    LocationDto updateLocation(int locationId, LocationRequest locationRequest);

    boolean deleteLocation(int id);

    LocationDto getLocation(int id);

    BaseLocationDto getLocationBase(int id);

    List<BaseLocationDto> getAllLocationByOrganizationId(int organizationId, String type);
}
