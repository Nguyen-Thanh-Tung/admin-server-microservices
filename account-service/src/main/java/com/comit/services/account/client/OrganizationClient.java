package com.comit.services.account.client;

import com.comit.services.account.client.request.OrganizationRequest;
import com.comit.services.account.client.response.OrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "organization-service")
public interface OrganizationClient {

    @GetMapping("/organizations/{organizationId}")
    ResponseEntity<OrganizationResponse> getOrganization(@RequestHeader("token") String token, @PathVariable("organizationId") Integer organizationId);

    @GetMapping("/organizations/name/{organizationName}")
    ResponseEntity<OrganizationResponse> getOrganization(@RequestHeader("token") String token, @PathVariable("organizationName") String organizationName);

    @PostMapping("/organizations")
    ResponseEntity<OrganizationResponse> addOrganization(@RequestHeader("token") String token, @RequestBody OrganizationRequest organization);
}
