package com.comit.services.history.server.controller;

import com.comit.services.history.client.response.AreaRestrictionCountResponse;
import com.comit.services.history.client.response.BaseResponse;
import com.comit.services.history.client.response.TimeKeepingCountResponse;
import com.comit.services.history.server.business.InOutHistoryBusiness;
import com.comit.services.history.server.business.NotificationHistoryBusiness;
import com.comit.services.history.server.constant.HistoryErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/histories")
public class HistoryController {
    @Autowired
    InOutHistoryBusiness inOutHistoryBusiness;

    @Autowired
    NotificationHistoryBusiness notificationHistoryBusiness;

    @GetMapping(value = "/time-keepings/count")
    public ResponseEntity<BaseResponse> getTimeKeepingCount() {
        int numberCheckInCurrentDay = inOutHistoryBusiness.getNumberCheckInCurrentDay();
        int numberUserNotificationInCurrenDay = notificationHistoryBusiness.getNumberUserNotificationInCurrenDay();
        int numberUserLateInMonth = notificationHistoryBusiness.getNumberLateInMonth();
        return new ResponseEntity<>(new TimeKeepingCountResponse(HistoryErrorCode.SUCCESS.getCode(), HistoryErrorCode.SUCCESS.getMessage(), numberCheckInCurrentDay, numberUserNotificationInCurrenDay, numberUserLateInMonth), HttpStatus.OK);
    }

    @GetMapping(value = "/area-restrictions/count")
    public ResponseEntity<BaseResponse> getAreaRestrictionCount(){
        int numberNotificationInDay = notificationHistoryBusiness.getNumberNotificationInDay();
        int numberNotificationNotResolve = notificationHistoryBusiness.getNumberNotificationNotResolve();
        int numberAreaRestrictionHasNotify = notificationHistoryBusiness.getNumberAreaRestrictionHasNotify();
        int numberAreaRestrictionHasNotifyNotResolveAndUsingRing = notificationHistoryBusiness.getNumberARHasNotifyNotResolveAndUsingRing();
        return new ResponseEntity<>(new AreaRestrictionCountResponse(HistoryErrorCode.SUCCESS.getCode(), HistoryErrorCode.SUCCESS.getMessage(), numberAreaRestrictionHasNotify, numberNotificationInDay, numberNotificationNotResolve, numberAreaRestrictionHasNotifyNotResolveAndUsingRing), HttpStatus.OK);
    }
}
