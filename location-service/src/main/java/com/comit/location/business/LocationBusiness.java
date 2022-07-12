package com.comit.location.business;

import com.comit.location.controller.request.LocationRequest;
import com.comit.location.model.dto.LocationDto;
import com.comit.location.model.entity.Location;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LocationBusiness {
    Page<Location> getLocationPage(int page, int size, String search);

    List<LocationDto> getAllLocation(List<Location> content);

    LocationDto addLocation(LocationRequest addLocationRequest);

    LocationDto updateLocation(int locationId, LocationRequest locationRequest);

    boolean deleteLocation(int id);

    LocationDto getLocation(int id);

    List<LocationDto> getAllLocationByOrganizationId(int organizationId);
}
