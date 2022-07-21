package com.comit.services.camera.client;

import com.comit.services.camera.client.response.LocationResponse;
import com.comit.services.camera.client.response.OrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/users/current/location")
    ResponseEntity<LocationResponse> getLocationOfCurrentUser(@RequestHeader String token);

    @GetMapping("/users/current/organization")
    ResponseEntity<OrganizationResponse> getOrganizationOfCurrentUser(@RequestHeader String token);
}
