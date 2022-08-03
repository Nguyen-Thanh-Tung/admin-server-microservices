package com.comit.services.camera.client;

import com.comit.services.camera.client.response.AreaRestrictionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "area-restriction-service")
public interface AreaRestrictionClient {
    @GetMapping("/area-restrictions/{areaRestrictionId}")
    ResponseEntity<AreaRestrictionResponse> getAreaRestriction(@RequestHeader("token") String token, @PathVariable Integer areaRestrictionId);
}
