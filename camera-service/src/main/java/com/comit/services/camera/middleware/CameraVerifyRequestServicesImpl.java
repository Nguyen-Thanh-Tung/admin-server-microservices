package com.comit.services.camera.middleware;

import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.controller.request.CameraRequest;
import com.comit.services.camera.exception.RestApiException;
import com.comit.services.camera.service.CameraServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CameraVerifyRequestServicesImpl implements CameraVerifyRequestServices {

    @Autowired
    private CameraServices cameraServices;

    @Override
    public void verifyAddOrUpdateCameraRequest(CameraRequest request) {
        String ipAddress = request.getIpAddress();
        Integer locationId = request.getLocationId();
        Integer areaRestrictionId = request.getAreaRestrictionId();
        String name = request.getName();
        String type = request.getType();
        if (name == null || name.trim().isEmpty()) {
            throw new RestApiException(CameraErrorCode.MISSING_NAME_FIELD);
        }

        if (ipAddress == null || ipAddress.trim().isEmpty()) {
            throw new RestApiException(CameraErrorCode.MISSING_IP_ADDRESS_FIELD);
        }

        if (cameraServices.isTimeKeepingModule() && locationId == null) {
            throw new RestApiException(CameraErrorCode.MISSING_LOCATION_ID_FIELD);
        }

        if (cameraServices.isAreaRestrictionModule() && areaRestrictionId == null) {
            throw new RestApiException(CameraErrorCode.MISSING_AREA_RESTRICTION_ID_FIELD);
        }

        if (cameraServices.isBehaviorModule() && areaRestrictionId == null) {
            throw new RestApiException(CameraErrorCode.MISSING_AREA_RESTRICTION_ID_FIELD);
        }

        if (cameraServices.isTimeKeepingModule() && (type == null || type.trim().isEmpty())) {
            throw new RestApiException(CameraErrorCode.MISSING_TYPE_FIELD);
        }
    }
}
