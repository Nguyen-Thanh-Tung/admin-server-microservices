package com.comit.location.client;

import com.comit.location.client.response.CheckModuleResponse;
import com.comit.location.client.response.CheckRoleResponse;
import com.comit.location.client.response.UserListResponse;
import com.comit.location.model.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "account-service")
public interface AccountClient {
	@GetMapping("/roles/location")
	ResponseEntity<CheckRoleResponse> hasPermissionManagerLocation(String timeKeepingType);

	@GetMapping("/modules/check-time-keeping")
	ResponseEntity<CheckModuleResponse> isTimeKeepingModule();

	@GetMapping("/modules/check-area-restriction")
	ResponseEntity<CheckModuleResponse> isAreaRestrictionModule();

	@GetMapping("/users/location/{locationId}")
	ResponseEntity<UserListResponse> getUserOfLocation(@PathVariable(name = "locationId") int locationId);
}
