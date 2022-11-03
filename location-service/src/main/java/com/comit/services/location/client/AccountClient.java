package com.comit.services.location.client;

import com.comit.services.location.client.response.CheckRoleResponseClient;
import com.comit.services.location.client.response.CountResponseClient;
import com.comit.services.location.client.response.UserResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/roles/location/type/{type}")
    ResponseEntity<CheckRoleResponseClient> hasPermissionManagerLocation(@RequestHeader String token, @PathVariable String type);

    @GetMapping("/users/location/{locationId}/number-user")
    ResponseEntity<CountResponseClient> getNumberUserOfLocation(@RequestHeader String token, @PathVariable Integer locationId);

    @GetMapping("/users/current")
    ResponseEntity<UserResponseClient> getCurrentUser(@RequestHeader String token);
}
