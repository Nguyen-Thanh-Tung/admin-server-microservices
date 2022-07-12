package com.comit.location.client;

import com.comit.location.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "time-keeping-notification-service")
public interface TimeKeepingNotificationClient {
    @PostMapping("/time-keeping-notifications/location/{locationId}")
    ResponseEntity<BaseResponse> addTimeKeepingNotification(@PathVariable(name = "locationId") int locationId);

    @DeleteMapping("/time-keeping-notifications/location/{locationId}")
    ResponseEntity<BaseResponse> deleteTimeKeepingNotification(@PathVariable(name = "locationId") int locationId);
}
