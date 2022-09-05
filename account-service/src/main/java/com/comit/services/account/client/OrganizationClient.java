package com.comit.services.account.client;

import com.comit.services.account.client.request.OrganizationRequestClient;
import com.comit.services.account.client.response.OrganizationResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "organization-service")
public interface OrganizationClient {

    @GetMapping("/organizations/{id}/base")
    ResponseEntity<OrganizationResponseClient> getOrganization(@RequestHeader String token, @PathVariable("id") Integer organizationId);

    @GetMapping("/organizations/name/{organizationName}")
    ResponseEntity<OrganizationResponseClient> getOrganization(@RequestHeader String token, @PathVariable("organizationName") String organizationName);

    @PostMapping("/organizations")
    ResponseEntity<OrganizationResponseClient> addOrganization(@RequestHeader String token, @RequestBody OrganizationRequestClient organization);
}
