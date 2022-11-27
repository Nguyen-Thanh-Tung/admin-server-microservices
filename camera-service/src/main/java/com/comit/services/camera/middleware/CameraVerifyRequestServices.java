package com.comit.services.camera.middleware;

import com.comit.services.camera.controller.request.CameraPolygonsRequest;
import com.comit.services.camera.controller.request.CameraRequest;

public interface CameraVerifyRequestServices {
    void verifyAddOrUpdateCameraRequest(CameraRequest request);

    void verifyUpdateCameraPolygons(CameraPolygonsRequest request);
}
