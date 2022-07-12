package com.comit.location.client;

import com.comit.location.client.response.CameraListResponse;
import com.comit.location.model.entity.Camera;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "camera-service")
public interface CameraClient {
    @GetMapping("/cameras/location/{locationId}")
    ResponseEntity<CameraListResponse> getCameraOfLocation(@PathVariable(name = "locationId") int locationId);
}
