package com.comit.services.account.client;

import com.comit.services.account.client.response.LocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "location-service")
public interface LocationClient {
	@GetMapping("/locations/{locationId}")
	ResponseEntity<LocationResponse> getLocation(@RequestHeader("token") String token, @PathVariable("locationId") Integer locationId);

}
