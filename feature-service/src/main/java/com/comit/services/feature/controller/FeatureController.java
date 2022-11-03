package com.comit.services.feature.controller;

import com.comit.services.feature.business.FeatureBusiness;
import com.comit.services.feature.constant.FeatureErrorCode;
import com.comit.services.feature.controller.request.FeatureRequest;
import com.comit.services.feature.controller.response.BaseResponse;
import com.comit.services.feature.controller.response.FeatureListResponse;
import com.comit.services.feature.controller.response.FeatureResponse;
import com.comit.services.feature.model.dto.FeatureDto;
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
    public ResponseEntity<BaseResponse> getAllFeatures() {
        List<FeatureDto> featureDtos = featureBusiness.getAllFeature();

        return new ResponseEntity<>(new FeatureListResponse(FeatureErrorCode.SUCCESS, featureDtos), HttpStatus.OK);
    }

    /**
     * Get feature info
     *
     * @param id : feature id
     * @return FeatureResponse
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> getFeatureInfo(@PathVariable int id) {
        FeatureDto featureDto = featureBusiness.getFeature(id);
        return new ResponseEntity<>(new FeatureResponse(FeatureErrorCode.SUCCESS, featureDto), HttpStatus.OK);
    }

    /**
     * Add feature
     *
     * @param featureRequest FeatureRequest
     * @return FeatureResponse
     */
    @PostMapping(value = "")
    public ResponseEntity<BaseResponse> addFeature(@RequestBody FeatureRequest featureRequest) {
        FeatureDto featureDto = featureBusiness.addFeature(featureRequest);
        return new ResponseEntity<>(new FeatureResponse(FeatureErrorCode.SUCCESS, featureDto), HttpStatus.OK);
    }
}
