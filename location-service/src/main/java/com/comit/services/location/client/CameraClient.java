package com.comit.services.location.client;

import com.comit.services.location.client.response.CameraListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "camera-service")
public interface CameraClient {
    @GetMapping("/cameras/location/{locationId}")
    ResponseEntity<CameraListResponse> getCameraOfLocation(@PathVariable Integer locationId);
}
