package com.comit.services.camera.server.middleware;

import com.comit.services.camera.client.request.CameraRequest;

public interface CameraVerifyRequestServices {
    void verifyAddOrUpdateCameraRequest(CameraRequest request);
}
