package com.comit.services.location.server.middleware;

import com.comit.services.location.client.request.LocationRequest;
import com.comit.services.location.server.constant.LocationErrorCode;
import com.comit.services.location.server.exception.RestApiException;
import org.springframework.stereotype.Service;

@Service
public class LocationVerifyRequestServicesImpl implements LocationVerifyRequestServices {
    @Override
    public void verifyAddOrUpdateLocationRequest(LocationRequest request) {
        String name = request.getName();
        String code = request.getCode();
        String type = request.getType();
        if (name == null || name.trim().isEmpty()) {
            throw new RestApiException(LocationErrorCode.MISSING_NAME_FIELD);
        }

        if (code == null || code.trim().isEmpty()) {
            throw new RestApiException(LocationErrorCode.MISSING_LOCATION_CODE_FIELD);
        }

        if (type == null || type.trim().isEmpty()) {
            throw new RestApiException(LocationErrorCode.MISSING_LOCATION_TYPE_FIELD);
        }
    }
}
