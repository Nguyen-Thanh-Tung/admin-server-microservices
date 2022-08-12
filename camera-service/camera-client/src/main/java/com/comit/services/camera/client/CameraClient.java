package com.comit.services.camera.client;

import com.comit.services.camera.client.response.CameraResponse;
import com.comit.services.camera.client.response.CountCameraResponse;

public interface CameraClient {
    CountCameraResponse getNumberCameraOfAreaRestriction(String token, int areaRestrictionId);

    CountCameraResponse getNumberCameraOfLocation(String token, Integer locationId);

    CameraResponse getCamera(String token, Integer id);
}
