package com.comit.services.history.service;

import com.comit.services.history.client.data.*;

public interface HistoryServices {

    LocationDto getLocation(Integer locationId);

    LocationDto getLocationOfCurrentUser();

    AreaRestrictionDto getAreaRestriction(Integer locationId, int areaRestrictionId);

    CameraDto getCamera(Integer cameraId);

    EmployeeDto getEmployee(Integer employeeId);

    NotificationMethodDto getNotificationMethodOfAreaRestriction(Integer areaRestrictionId);

    MetadataDto getMetadata(Integer imageId);
}
