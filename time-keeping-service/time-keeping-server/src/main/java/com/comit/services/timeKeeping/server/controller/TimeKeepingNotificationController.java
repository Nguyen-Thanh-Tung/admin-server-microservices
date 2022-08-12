package com.comit.services.timeKeeping.server.controller;

import com.comit.services.timeKeeping.client.dto.TimeKeepingNotificationDto;
import com.comit.services.timeKeeping.client.request.TimeKeepingNotificationRequest;
import com.comit.services.timeKeeping.client.response.BaseResponse;
import com.comit.services.timeKeeping.client.response.TimeKeepingNotificationResponse;
import com.comit.services.timeKeeping.server.business.TimeKeepingNotificationBusiness;
import com.comit.services.timeKeeping.server.constant.TimeKeepingErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/time-keeping-notifications")
public class TimeKeepingNotificationController {
    @Autowired
    private TimeKeepingNotificationBusiness timeKeepingNotificationBusiness;

    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getTimeKeepingNotification() {
        TimeKeepingNotificationDto timeKeepingNotificationDto = timeKeepingNotificationBusiness.getTimeKeepingNotification();
        return new ResponseEntity<>(new TimeKeepingNotificationResponse(TimeKeepingErrorCode.SUCCESS.getCode(), TimeKeepingErrorCode.SUCCESS.getMessage(), timeKeepingNotificationDto), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> updateTimeKeepingNotification(
            @PathVariable int id,
            @RequestBody TimeKeepingNotificationRequest timeKeepingNotificationRequest
    ) {
        TimeKeepingNotificationDto timeKeepingNotificationDto = timeKeepingNotificationBusiness.updateTimeKeepingNotification(id, timeKeepingNotificationRequest);
        return new ResponseEntity<>(new TimeKeepingNotificationResponse(TimeKeepingErrorCode.SUCCESS.getCode(), TimeKeepingErrorCode.SUCCESS.getMessage(), timeKeepingNotificationDto), HttpStatus.OK);
    }

    @PostMapping("/location/{locationId}")
    ResponseEntity<BaseResponse> addTimeKeepingNotification(@PathVariable Integer locationId) {
        boolean addSuccess = timeKeepingNotificationBusiness.addTimeKeepingNotificationForLocation(locationId);
        if (addSuccess) {
            return new ResponseEntity<>(new BaseResponse(TimeKeepingErrorCode.SUCCESS.getCode(), TimeKeepingErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(TimeKeepingErrorCode.FAIL.getCode(), TimeKeepingErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }

    @DeleteMapping("/location/{locationId}")
    ResponseEntity<BaseResponse> deleteTimeKeepingNotification(@PathVariable Integer locationId) {
        boolean deleteSuccess = timeKeepingNotificationBusiness.deleteTimeKeepingNotificationOfLocation(locationId);
        if (deleteSuccess) {
            return new ResponseEntity<>(new BaseResponse(TimeKeepingErrorCode.SUCCESS.getCode(), TimeKeepingErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(TimeKeepingErrorCode.FAIL.getCode(), TimeKeepingErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }
}
