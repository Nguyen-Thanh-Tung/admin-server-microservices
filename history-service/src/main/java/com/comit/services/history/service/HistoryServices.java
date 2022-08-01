package com.comit.services.history.service;

import com.comit.services.history.model.entity.AreaRestriction;
import com.comit.services.history.model.entity.Camera;
import com.comit.services.history.model.entity.Employee;
import com.comit.services.history.model.entity.Location;

public interface HistoryServices {

    Location getLocation(Integer locationId);

    Location getLocationOfCurrentUser();

    AreaRestriction getAreaRestriction(Integer locationId, int areaRestrictionId);

    Camera getCamera(Integer cameraId);

    Employee getEmployee(Integer employeeId);
}
