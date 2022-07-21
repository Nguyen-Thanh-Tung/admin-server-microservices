package com.comit.services.account.client;

import com.comit.services.account.client.request.OrganizationRequest;
import com.comit.services.account.client.response.OrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "organization-service")
public interface OrganizationClient {

	@GetMapping("/organizations/{organizationId}")
	ResponseEntity<OrganizationResponse> getOrganization(@PathVariable("organizationId") Integer organizationId);

	@GetMapping("/organizations/name/{organizationName}")
	ResponseEntity<OrganizationResponse> getOrganization(@PathVariable("organizationName") String organizationName);

	@PostMapping("/organizations")
	ResponseEntity<OrganizationResponse> addOrganization(@RequestBody OrganizationRequest organization);
}
