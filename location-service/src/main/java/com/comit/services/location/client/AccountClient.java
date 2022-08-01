package com.comit.services.location.client;

import com.comit.services.location.client.response.CheckRoleResponse;
import com.comit.services.location.client.response.OrganizationResponse;
import com.comit.services.location.client.response.UserListResponse;
import com.comit.services.location.client.response.UserResponse;
import com.comit.services.location.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/roles/location/type/{type}")
    ResponseEntity<CheckRoleResponse> hasPermissionManagerLocation(@RequestHeader String token, @PathVariable String type);

    @GetMapping("/users/location/{locationId}")
    ResponseEntity<UserListResponse> getUserOfLocation(@RequestHeader String token, @PathVariable Integer locationId);

    @GetMapping("/users/current/organization")
    ResponseEntity<OrganizationResponse> getOrganizationOfCurrentUser(@RequestHeader String token);

    @GetMapping("/users/current/user")
    ResponseEntity<UserResponse> getCurrentUser(@RequestHeader String token);
}
