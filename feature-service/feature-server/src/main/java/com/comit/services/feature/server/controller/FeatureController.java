package com.comit.services.feature.server.controller;

import com.comit.services.feature.client.dto.FeatureDto;
import com.comit.services.feature.client.request.FeatureRequest;
import com.comit.services.feature.client.response.FeatureListResponse;
import com.comit.services.feature.client.response.FeatureResponse;
import com.comit.services.feature.server.business.FeatureBusiness;
import com.comit.services.feature.server.constant.FeatureErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/features")
public class FeatureController {
    @Autowired
    private FeatureBusiness featureBusiness;

    /**
     * Get all features
     *
     * @return FeaturesListResponse
     */
    @GetMapping(value = "")
    public ResponseEntity<FeatureListResponse> getAllFeatures() {
        List<FeatureDto> featureDtos = featureBusiness.getAllFeature();

        return new ResponseEntity<>(new FeatureListResponse(FeatureErrorCode.SUCCESS.getCode(), FeatureErrorCode.SUCCESS.getMessage(), featureDtos), HttpStatus.OK);
    }

    /**
     * Get feature info
     *
     * @param id : feature id
     * @return FeatureResponse
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<FeatureResponse> getFeatureInfo(@PathVariable int id) {
        FeatureDto featureDto = featureBusiness.getFeature(id);
        return new ResponseEntity<>(new FeatureResponse(FeatureErrorCode.SUCCESS.getCode(), FeatureErrorCode.SUCCESS.getMessage(), featureDto), HttpStatus.OK);
    }

    /**
     * Add feature
     *
     * @param featureRequest FeatureRequest
     * @return FeatureResponse
     */
    @PostMapping(value = "")
    public ResponseEntity<FeatureResponse> addFeature(@RequestBody FeatureRequest featureRequest) {
        FeatureDto featureDto = featureBusiness.addFeature(featureRequest);
        return new ResponseEntity<>(new FeatureResponse(FeatureErrorCode.SUCCESS.getCode(), FeatureErrorCode.SUCCESS.getMessage(), featureDto), HttpStatus.OK);
    }
}
