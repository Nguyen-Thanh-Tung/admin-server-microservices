package com.comit.services.location.client;

import com.comit.services.location.client.response.OrganizationResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "organization-service")
public interface OrganizationClient {
    @GetMapping("/organizations/{id}")
    ResponseEntity<OrganizationResponseClient> getOrganizationById(@RequestHeader String token, @PathVariable("id") int organizationId);
}
