package com.comit.services.history.service;

import com.comit.services.history.model.entity.*;

public interface HistoryServices {

    Location getLocation(Integer locationId);

    Location getLocationOfCurrentUser();

    AreaRestriction getAreaRestriction(Integer locationId, int areaRestrictionId);

    Camera getCamera(Integer cameraId);

    Employee getEmployee(Integer employeeId);

    NotificationMethod getNotificationMethodOfAreaRestriction(Integer areaRestrictionId);

    Metadata getMetadata(Integer imageId);
}
