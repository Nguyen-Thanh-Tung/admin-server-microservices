package com.comit.services.feature.client;

import com.comit.services.feature.client.response.CheckRoleResponse;
import com.comit.services.feature.client.response.RoleListResponse;
import com.comit.services.feature.client.response.RoleResponse;
import com.comit.services.feature.client.response.UserListResponse;
import org.aspectj.weaver.ast.Not;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/roles/is-super-admin")
    ResponseEntity<CheckRoleResponse> isCurrentUserSuperAdmin(@RequestHeader("token") String token);

    @GetMapping("/users/current/roles")
    ResponseEntity<RoleListResponse> getRolesOfCurrentUser(@RequestHeader(value = "token") String token);

    @GetMapping("/roles/{roleId}/users")
    ResponseEntity<UserListResponse> getUsersOfRole(@RequestHeader("token") String token, @PathVariable Integer roleId);

    @GetMapping("/roles/name/{roleName}")
    ResponseEntity<RoleResponse> getRoleByName(@RequestHeader("token") String token, @PathVariable String roleName);
}
