package com.comit.services.account.client;

import com.comit.services.account.client.request.FeatureRequest;
import com.comit.services.account.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "feature-service")
public interface FeatureClient {

    @PostMapping("/features")
    ResponseEntity<BaseResponse> addFeature(@RequestBody FeatureRequest featureRequest);
}
