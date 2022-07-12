package com.comit.organization.client;

import com.comit.organization.client.response.LocationListResponse;
import com.comit.organization.model.entity.Location;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "location-service")
public interface LocationClient {
	@GetMapping("/locations/organization/{organizationId}")
	ResponseEntity<LocationListResponse> getLocationsByOrganizationId(@PathVariable(name = "organizationId") int organizationId);
}
