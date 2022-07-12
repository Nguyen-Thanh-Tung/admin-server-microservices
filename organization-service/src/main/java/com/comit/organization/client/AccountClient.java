package com.comit.organization.client;

import com.comit.organization.client.response.CheckRoleResponse;
import com.comit.organization.controller.response.UserListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountClient {
	@GetMapping("/roles/organization")
	ResponseEntity<CheckRoleResponse> hasPermissionManageOrganization(@RequestHeader("token") String token);

	@GetMapping("/users/organization/{organizationId}")
	ResponseEntity<UserListResponse> getUsersByOrganizationId(@PathVariable(name = "organizationId") int organizationId);
}
