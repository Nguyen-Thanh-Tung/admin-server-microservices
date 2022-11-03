package com.comit.services.areaRestriction.client;

import com.comit.services.areaRestriction.client.response.CountCameraResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("camera-service")
public interface CameraClient {
    @GetMapping("/cameras/area-restriction/{areaRestrictionId}/number-camera")
    ResponseEntity<CountCameraResponseClient> getNumberCameraOfAreaRestriction(@RequestHeader String token, @PathVariable("areaRestrictionId") int areaRestrictionId);

}
