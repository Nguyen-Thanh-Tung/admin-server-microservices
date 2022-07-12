package com.comit.location.client;

import com.comit.location.client.response.OrganizationResponse;
import com.comit.location.model.entity.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "organization-service")
public interface OrganizationClient {
	@GetMapping("/organizations/user/current")
	ResponseEntity<OrganizationResponse> getOrganizationOfCurrentUser();
}
