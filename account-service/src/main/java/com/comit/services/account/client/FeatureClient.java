package com.comit.services.account.client;

import com.comit.services.account.client.request.FeatureRequestClient;
import com.comit.services.account.client.response.BaseResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "feature-service")
public interface FeatureClient {

    @PostMapping("/features")
    ResponseEntity<BaseResponseClient> addFeature(@RequestHeader String token, @RequestBody FeatureRequestClient featureRequestClient);
}
