package com.comit.services.camera.client;

import com.comit.services.camera.client.response.CheckRoleResponseClient;
import com.comit.services.camera.client.response.RoleListResponseClient;
import com.comit.services.camera.client.response.UserResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/users/current")
    ResponseEntity<UserResponseClient> getCurrentUser(@RequestHeader String token);

    @GetMapping("/users/current/roles")
    ResponseEntity<RoleListResponseClient> getRolesOfCurrentUser(@RequestHeader String token);

    @GetMapping("/users/current/check-role")
    ResponseEntity<CheckRoleResponseClient> hasRole(@RequestHeader String token, @RequestParam String roleNeedCheck);
}
