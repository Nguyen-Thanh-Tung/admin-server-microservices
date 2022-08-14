package com.comit.services.feature.client;

import com.comit.services.feature.client.response.CheckRoleResponseClient;
import com.comit.services.feature.client.response.RoleListResponseClient;
import com.comit.services.feature.client.response.RoleResponseClient;
import com.comit.services.feature.client.response.UserListResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/roles/is-super-admin")
    ResponseEntity<CheckRoleResponseClient> isCurrentUserSuperAdmin(@RequestHeader String token);

    @GetMapping("/users/current/roles")
    ResponseEntity<RoleListResponseClient> getRolesOfCurrentUser(@RequestHeader String token);

    @GetMapping("/roles/{roleId}/users")
    ResponseEntity<UserListResponseClient> getUsersOfRole(@RequestHeader String token, @PathVariable Integer roleId);

    @GetMapping("/roles/name/{roleName}")
    ResponseEntity<RoleResponseClient> getRoleByName(@RequestHeader String token, @PathVariable String roleName);
}
