package com.comit.services.feature.client;

import com.comit.services.feature.client.request.FeatureRequest;
import com.comit.services.feature.client.response.BaseResponse;

public interface FeatureClient {
    BaseResponse addFeature(String token, FeatureRequest featureRequest);
}
