package com.comit.services.location.client;

import com.comit.services.location.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "time-keeping-service")
public interface TimeKeepingClient {
    @PostMapping("/shifts/location/{locationId}")
    ResponseEntity<BaseResponse> addShiftsForLocation(@RequestHeader String token, @PathVariable Integer locationId,
                                                      @RequestHeader String internal);

    @DeleteMapping("/shifts/location/{locationId}")
    ResponseEntity<BaseResponse> deleteShiftsOfLocation(@RequestHeader String token, @PathVariable Integer locationId,
                                                        @RequestHeader String internal);

    @PostMapping("/time-keeping-notifications/location/{locationId}")
    ResponseEntity<BaseResponse> addTimeKeepingNotification(@RequestHeader String token, @PathVariable Integer locationId,
                                                            @RequestHeader String internal);

    @DeleteMapping("/time-keeping-notifications/location/{locationId}")
    ResponseEntity<BaseResponse> deleteTimeKeepingNotification(@RequestHeader String token, @PathVariable Integer locationId,
                                                               @RequestHeader String internal);
}
