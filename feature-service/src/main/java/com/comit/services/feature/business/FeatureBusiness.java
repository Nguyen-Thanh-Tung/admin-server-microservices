package com.comit.services.feature.business;

import com.comit.services.feature.controller.request.FeatureRequest;
import com.comit.services.feature.model.dto.FeatureDto;

import java.util.List;

public interface FeatureBusiness {
    List<FeatureDto> getAllFeature();

    FeatureDto addFeature(FeatureRequest featureRequest);

    FeatureDto getFeature(int id);
}
