package com.comit.services.feature.client;

import com.comit.services.feature.client.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/roles/is-super-admin")
    ResponseEntity<CheckRoleResponseClient> isCurrentUserSuperAdmin(@RequestHeader String token);

    @GetMapping("/users/current/roles")
    ResponseEntity<RoleListResponseClient> getRolesOfCurrentUser(@RequestHeader String token);

    @GetMapping("/users/number-user-of-roles")
    ResponseEntity<CountResponseClient> getNumberUserOfRoles(@RequestHeader String token, @RequestParam("role_ids") String roleIds);

    @GetMapping("/users/number-organization-of-roles")
    ResponseEntity<CountResponseClient> getNumberOrganizationOfRoles(@RequestHeader String token, @RequestParam("role_ids") String roleIds);

    @GetMapping("/roles/name/{roleName}")
    ResponseEntity<RoleResponseClient> getRoleByName(@RequestHeader String token, @PathVariable String roleName);

    @GetMapping("/users/current")
    ResponseEntity<UserResponseClient> getCurrentUser(@RequestHeader String token);
}
