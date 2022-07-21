package com.comit.services.organization.client;

import com.comit.services.organization.client.response.CheckRoleResponse;
import com.comit.services.organization.controller.response.UserListResponse;
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
	ResponseEntity<UserListResponse> getUsersByOrganizationId(@RequestHeader("token") String token, @PathVariable(name = "organizationId") int organizationId);
}
