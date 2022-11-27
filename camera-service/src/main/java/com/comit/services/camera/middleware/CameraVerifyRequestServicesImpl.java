package com.comit.services.camera.middleware;

import com.comit.services.camera.client.AccountClient;
import com.comit.services.camera.client.response.RoleListResponseClient;
import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.constant.Const;
import com.comit.services.camera.controller.request.CameraPolygonsRequest;
import com.comit.services.camera.controller.request.CameraRequest;
import com.comit.services.camera.exception.RestApiException;
import com.comit.services.camera.service.CameraServices;
import com.comit.services.camera.util.ValidateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class CameraVerifyRequestServicesImpl implements CameraVerifyRequestServices {

    @Autowired
    private CameraServices cameraServices;

    @Autowired
    private ValidateField validateField;

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void verifyAddOrUpdateCameraRequest(CameraRequest request) {
        RoleListResponseClient roleListResponseClient = accountClient.getRolesOfCurrentUser(httpServletRequest.getHeader("token")).getBody();
        String ipAddress = request.getIpAddress();
        Integer locationId = request.getLocationId();
        Integer areaRestrictionId = request.getAreaRestrictionId();
        String name = request.getName();
        String type = request.getType();

        if (name == null || name.trim().isEmpty()) {
            throw new RestApiException(CameraErrorCode.MISSING_NAME_FIELD);
        } else {
            request.setName(name.trim());
        }

        if (ipAddress == null || ipAddress.trim().isEmpty()) {
            throw new RestApiException(CameraErrorCode.MISSING_IP_ADDRESS_FIELD);
        } else {
            request.setIpAddress(request.getIpAddress().trim());
        }

        if (cameraServices.isAreaRestrictionModule() || cameraServices.isBehaviorModule()) {
            if (areaRestrictionId == null) {
                throw new RestApiException(CameraErrorCode.MISSING_AREA_RESTRICTION_ID_FIELD);
            }
            if (type != null && !type.trim().isEmpty() && !Objects.equals(type.trim(), Const.GSKVHC)) {
                throw new RestApiException(CameraErrorCode.INVALID_TYPE_FIELD_AREA);
            }
        }

        if (cameraServices.isTimeKeepingModule()) {
            if (locationId == null) {
                throw new RestApiException(CameraErrorCode.MISSING_LOCATION_ID_FIELD);
            }
            if (type == null || type.trim().isEmpty() || !Const.TK_CAMERA_TYPE.contains(type.trim())) {
                throw new RestApiException(CameraErrorCode.INVALID_TYPE_FIELD_TK);
            } else {
                request.setType(type.trim());
            }
            if (areaRestrictionId != null) {
                throw new RestApiException(CameraErrorCode.INVALID_AREA_RESTRICTION_ID_FIELD);
            }
        }

        if (!cameraServices.isMatchRolesAndModule(roleListResponseClient)) {
            throw new RestApiException(CameraErrorCode.PERMISSION_DENIED);
        }

        if (locationId != null) { // Sister NgocCRD order
            if (!cameraServices.isMatchLocationTypeAndModule(locationId)) {
                throw new RestApiException(CameraErrorCode.INVALID_LOCATION_ID_FIELD);
            }
        }
    }

    @Override
    public void verifyUpdateCameraPolygons(CameraPolygonsRequest request) {
        if (request.getPolygons() == null || request.getPolygons().trim().equals("")) {
            throw new RestApiException(CameraErrorCode.MISSING_POLYGONS_FIELD);
        } else if (!validateField.validStringPolygons(request.getPolygons())) {
            throw new RestApiException(CameraErrorCode.INVALID_POLYGONS_FIELD);
        }
    }
}
