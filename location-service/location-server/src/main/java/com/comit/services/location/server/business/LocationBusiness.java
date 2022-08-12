package com.comit.services.location.server.business;

import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.location.client.request.LocationRequest;
import com.comit.services.location.server.model.Location;
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
