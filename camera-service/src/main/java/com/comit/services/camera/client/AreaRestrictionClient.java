package com.comit.services.camera.client;

import com.comit.services.camera.client.response.AreaRestrictionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "area-restriction-service")
public interface AreaRestrictionClient {
	@GetMapping("/areaRestrictions/{areaRestrictionId}")
	ResponseEntity<AreaRestrictionResponse> getAreaRestriction(@PathVariable Integer areaRestrictionId);
}
