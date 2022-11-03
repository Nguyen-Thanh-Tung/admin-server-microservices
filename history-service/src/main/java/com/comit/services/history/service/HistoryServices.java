package com.comit.services.history.service;

import com.comit.services.history.client.data.*;

public interface HistoryServices {

    LocationDtoClient getLocation(Integer locationId);

    LocationDtoClient getLocationOfCurrentUser();

    AreaRestrictionDtoClient getAreaRestriction(Integer locationId, int areaRestrictionId);

    CameraDtoClient getCamera(Integer cameraId);

    EmployeeDtoClient getEmployee(Integer employeeId);

    NotificationMethodDtoClient getNotificationMethodOfAreaRestriction(Integer areaRestrictionId);

    MetadataDtoClient getMetadata(Integer imageId);
}
