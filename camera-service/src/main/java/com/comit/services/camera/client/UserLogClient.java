package com.comit.services.camera.client;

import com.comit.services.camera.client.request.UserLogRequest;
import com.comit.services.camera.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-log-service")
public interface UserLogClient {
    @PostMapping("/user-logs")
    ResponseEntity<BaseResponse> saveUserLog(@RequestHeader String token, @RequestBody UserLogRequest userLogRequest);
}