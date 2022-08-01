package com.comit.services.employee.client;

import com.comit.services.employee.client.response.LocationResponse;
import com.comit.services.employee.client.response.OrganizationResponse;
import com.comit.services.employee.client.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/users/current/location")
    ResponseEntity<LocationResponse> getLocationOfCurrentUser(@RequestHeader("token") String token);

    @GetMapping("/users/current/organization")
    ResponseEntity<OrganizationResponse> getOrganizationOfCurrentUser(@RequestHeader("token") String token);

    @GetMapping("/users/current/user")
    ResponseEntity<UserResponse> getCurrentUser(@RequestHeader String token);
}
