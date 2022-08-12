package com.comit.services.feature.server.middleware;

import com.comit.services.feature.client.request.FeatureRequest;
import com.comit.services.feature.server.constant.FeatureErrorCode;
import com.comit.services.feature.server.exception.RestApiException;
import org.springframework.stereotype.Service;

@Service
public class FeatureVerifyRequestServicesImpl implements FeatureVerifyRequestServices {
    @Override
    public void verifyAddFeatureRequest(FeatureRequest request) {
        String name = request.getName();
        if (name == null || name.trim().isEmpty()) {
            throw new RestApiException(FeatureErrorCode.MISSING_NAME_FIELD);
        }
    }
}
