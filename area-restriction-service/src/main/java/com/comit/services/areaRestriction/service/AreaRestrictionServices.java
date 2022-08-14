package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.client.data.EmployeeDtoClient;
import com.comit.services.areaRestriction.client.data.LocationDtoClient;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface AreaRestrictionServices {
    Page<AreaRestriction> getAreaRestrictionPage(Integer locationId, String search, Pageable paging);

    AreaRestriction getAreaRestriction(Integer locationId, Integer id);

    AreaRestriction getAreaRestriction(Integer locationId, String name);

    AreaRestriction updateAreaRestriction(AreaRestriction areaRestriction);

    boolean deleteAreaRestriction(AreaRestriction areaRestriction);

    LocationDtoClient getLocationOfCurrentUser();

    int getNumberNotificationOfAreaRestriction(AreaRestriction areaRestriction, Date startDay, Date toDate);

    EmployeeDtoClient getEmployee(Integer employeeId, Integer locationId);

    int getNumberCameraOfAreaRestriction(int areaRestrictionId);

    List<AreaRestriction> getAllAreaRestrictionOfManager(Integer managerId);
}
