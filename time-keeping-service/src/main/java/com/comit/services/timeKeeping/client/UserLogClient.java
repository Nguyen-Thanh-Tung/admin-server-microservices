package com.comit.services.timeKeeping.client;

import com.comit.services.timeKeeping.client.request.UserLogRequestClient;
import com.comit.services.timeKeeping.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-log-service")
public interface UserLogClient {
    @PostMapping("/user-logs")
    ResponseEntity<BaseResponse> saveUserLog(@RequestHeader String token, @RequestBody UserLogRequestClient userLogRequestClient);
}
