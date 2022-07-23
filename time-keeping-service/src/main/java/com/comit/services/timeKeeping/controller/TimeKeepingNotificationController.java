package com.comit.services.timeKeeping.controller;

import com.comit.services.timeKeeping.business.TimeKeepingNotificationBusiness;
import com.comit.services.timeKeeping.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.controller.request.TimeKeepingNotificationRequest;
import com.comit.services.timeKeeping.controller.response.BaseResponse;
import com.comit.services.timeKeeping.controller.response.TimeKeepingNotificationResponse;
import com.comit.services.timeKeeping.model.dto.TimeKeepingNotificationDto;
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
        return new ResponseEntity<>(new TimeKeepingNotificationResponse(TimeKeepingErrorCode.SUCCESS, timeKeepingNotificationDto), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> updateTimeKeepingNotification(
            @PathVariable int id,
            @RequestBody TimeKeepingNotificationRequest timeKeepingNotificationRequest
    ) {
        TimeKeepingNotificationDto timeKeepingNotificationDto = timeKeepingNotificationBusiness.updateTimeKeepingNotification(id, timeKeepingNotificationRequest);
        return new ResponseEntity<>(new TimeKeepingNotificationResponse(TimeKeepingErrorCode.SUCCESS, timeKeepingNotificationDto), HttpStatus.OK);
    }

    @PostMapping("/location/{locationId}")
    ResponseEntity<BaseResponse> addTimeKeepingNotification(@PathVariable Integer locationId) {
        boolean addSuccess = timeKeepingNotificationBusiness.addTimeKeepingNotificationForLocation(locationId);
        return new ResponseEntity<>(new BaseResponse(addSuccess ? TimeKeepingErrorCode.SUCCESS : TimeKeepingErrorCode.FAIL), HttpStatus.OK);
    }

    @DeleteMapping("/location/{locationId}")
    ResponseEntity<BaseResponse> deleteTimeKeepingNotification(@PathVariable Integer locationId) {
        boolean deleteSuccess = timeKeepingNotificationBusiness.deleteTimeKeepingNotificationOfLocation(locationId);
        return new ResponseEntity<>(new BaseResponse(deleteSuccess ? TimeKeepingErrorCode.SUCCESS : TimeKeepingErrorCode.FAIL), HttpStatus.OK);
    }
}
