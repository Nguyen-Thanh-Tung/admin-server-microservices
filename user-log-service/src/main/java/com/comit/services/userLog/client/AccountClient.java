package com.comit.services.userLog.client;

import com.comit.services.userLog.client.response.UserListResponseClient;
import com.comit.services.userLog.client.response.UserResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "account-service")
public interface AccountClient {
    @GetMapping("/users")
    ResponseEntity<UserListResponseClient> getAllUsersOfCurrentUser(@RequestHeader String token);

    @GetMapping("/users/current")
    ResponseEntity<UserResponseClient> getCurrentUser(@RequestHeader String token);

    @GetMapping("/users/{id}")
    ResponseEntity<UserResponseClient> getUserById(@RequestHeader String token, @PathVariable("id") Integer userId);
}
