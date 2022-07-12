package com.comit.location.service;

import com.comit.location.model.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationServices {
    Location getLocation(Integer organizationId, int locationId);

    Location getLocation(int locationId);

    Page<Location> getLocationPage(Integer organizationId, String type, String search, Pageable paging);

    Location saveLocation(Location location);

    boolean deleteLocation(int id);

    boolean existLocation(String code, Integer organizationId);

    List<Location> getAllLocationByOrganizationId(int organizationId);
}
