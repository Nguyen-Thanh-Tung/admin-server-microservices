package com.comit.services.location.service;

import com.comit.services.location.model.entity.*;
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

    Organization getOrganizationOfCurrentUser();

    boolean hasPermissionManagerLocation(String timeKeepingType);

    boolean isTimeKeepingModule();

    boolean isAreaRestrictionModule();

    boolean isBehaviorModule();

    void addShiftsForLocation(int locationId);

    void addTimeKeepingNotification(int locationId);

    List<Employee> getEmployeeOfLocation(int locationId);

    List<Camera> getCameraOfLocation(int locationId);

    List<User> getUserOfLocation(int locationId);

    void deleteShiftsOfLocation(int locationId);

    void deleteTimeKeepingNotification(int locationId);
}
