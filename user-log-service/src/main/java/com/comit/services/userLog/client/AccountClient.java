package com.comit.services.userLog.client;

import com.comit.services.userLog.client.response.UserListResponse;
import com.comit.services.userLog.client.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/users/current/users")
    ResponseEntity<UserListResponse> getAllUsersOfCurrentUser(@RequestHeader String token);

    @GetMapping("/users/current/user")
    ResponseEntity<UserResponse> getCurrentUser(@RequestHeader String token);
}
