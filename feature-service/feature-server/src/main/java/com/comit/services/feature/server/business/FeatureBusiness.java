package com.comit.services.feature.server.business;

import com.comit.services.feature.client.dto.FeatureDto;
import com.comit.services.feature.client.request.FeatureRequest;

import java.util.List;


public interface FeatureBusiness {
    List<FeatureDto> getAllFeature();

    FeatureDto addFeature(FeatureRequest featureRequest);

    FeatureDto getFeature(int id);
}
