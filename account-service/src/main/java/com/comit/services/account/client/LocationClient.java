package com.comit.services.account.client;

import com.comit.services.account.controller.response.LocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "location-service")
public interface LocationClient {
	@GetMapping("/locations/{locationId}")
	ResponseEntity<LocationResponse> getLocation(@PathVariable("locationId") Integer locationId);

}
