package com.comit.services.areaRestriction.client;

import com.comit.services.areaRestriction.client.response.CountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("camera-service")
public interface CameraClient {
    @GetMapping("/cameras/area-restriction/{areaRestrictionId}/number-camera")
    ResponseEntity<CountResponse> getNumberCameraOfAreaRestriction(@PathVariable int areaRestrictionId);
}
