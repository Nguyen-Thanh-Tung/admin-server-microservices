package com.comit.services.history.server.service;

import com.comit.services.areaRestriction.client.dto.AreaRestrictionDto;
import com.comit.services.areaRestriction.client.dto.NotificationMethodDto;
import com.comit.services.camera.client.dto.CameraDto;
import com.comit.services.employee.client.dto.EmployeeDto;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.metadata.client.dto.MetadataDto;

public interface HistoryServices {

    LocationDto getLocation(Integer locationId);

    LocationDto getLocationOfCurrentUser();

    AreaRestrictionDto getAreaRestriction(Integer locationId, int areaRestrictionId);

    CameraDto getCamera(Integer cameraId);

    EmployeeDto getEmployee(Integer employeeId);

    NotificationMethodDto getNotificationMethodOfAreaRestriction(Integer areaRestrictionId);

    MetadataDto getMetadata(Integer imageId);
}
