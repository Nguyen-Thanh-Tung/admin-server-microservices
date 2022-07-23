package com.comit.services.camera.client;

import com.comit.services.camera.client.response.LocationListResponse;
import com.comit.services.camera.client.response.LocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "location-service")
public interface LocationClient {
	@GetMapping("/locations/organization/{organizationId}")
	ResponseEntity<LocationListResponse> getLocationsByOrganizationId(@RequestHeader("token") String token, @PathVariable(name = "organizationId") int organizationId);

	@GetMapping("/locations/{id}")
	ResponseEntity<LocationResponse> getLocationById(@RequestHeader("token") String token, @PathVariable Integer id);
}
