package com.comit.services.location.client;

import com.comit.services.location.client.response.CountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "camera-service")
public interface CameraClient {
    @GetMapping("/cameras/location/{locationId}/number-camera")
    ResponseEntity<CountResponse> getNumberCameraOfLocation(@RequestHeader String token, @PathVariable Integer locationId);
}
