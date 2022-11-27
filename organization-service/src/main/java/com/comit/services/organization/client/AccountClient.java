package com.comit.services.organization.client;

import com.comit.services.organization.client.response.CheckRoleResponseClient;
import com.comit.services.organization.client.response.CountUserResponse;
import com.comit.services.organization.client.response.UserResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/roles/organization")
    ResponseEntity<CheckRoleResponseClient> hasPermissionManageOrganization(@RequestHeader String token);

    @GetMapping("/users/organization/{organizationId}/number-all-user")
    ResponseEntity<CountUserResponse> getNumberUserOfOrganization(@RequestHeader String token, @PathVariable(name = "organizationId") int organizationId);

    @GetMapping("/users/current")
    ResponseEntity<UserResponseClient> getCurrentUser(@RequestHeader String token);
    @GetMapping("/users/current/check-role")
    ResponseEntity<CheckRoleResponseClient> hasRole(@RequestHeader String token, @RequestParam String roleNeedCheck);

}
