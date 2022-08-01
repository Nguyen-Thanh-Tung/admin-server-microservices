package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.model.entity.AreaRestriction;
import com.comit.services.areaRestriction.model.entity.Employee;
import com.comit.services.areaRestriction.model.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface AreaRestrictionServices {
    Page<AreaRestriction> getAreaRestrictionPage(Integer locationId, String search, Pageable paging);

    AreaRestriction getAreaRestriction(Integer locationId, Integer id);

    AreaRestriction getAreaRestriction(Integer locationId, String name);

    AreaRestriction updateAreaRestriction(AreaRestriction areaRestriction);

    boolean deleteAreaRestriction(AreaRestriction areaRestriction);

    Location getLocationOfCurrentUser();

    int getNumberNotificationOfAreaRestriction(AreaRestriction areaRestriction, Date startDay, Date toDate);

    Employee getEmployee(Integer managerId, Integer locationId);

    int getNumberCameraOfAreaRestriction(int areaRestrictionId);

    int getNumberAreaEmployeeTimeOfAreaRestriction(int areaRestrictionId);
}
