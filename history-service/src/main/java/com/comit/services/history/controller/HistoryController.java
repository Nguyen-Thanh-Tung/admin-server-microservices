package com.comit.services.history.controller;

import com.comit.services.history.business.InOutHistoryBusiness;
import com.comit.services.history.business.NotificationHistoryBusiness;
import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.controller.response.AreaRestrictionCountResponse;
import com.comit.services.history.controller.response.BaseResponse;
import com.comit.services.history.controller.response.TimeKeepingCountResponse;
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
        return new ResponseEntity<>(new TimeKeepingCountResponse(HistoryErrorCode.SUCCESS, numberCheckInCurrentDay, numberUserNotificationInCurrenDay, numberUserLateInMonth), HttpStatus.OK);
    }

    @GetMapping(value = "/area-restrictions/count")
    public ResponseEntity<BaseResponse> getAreaRestrictionCount(){
        int numberNotificationInDay = notificationHistoryBusiness.getNumberNotificationInDay();
        int numberNotificationNotResolve = notificationHistoryBusiness.getNumberNotificationNotResolve();
        int numberAreaRestrictionHasNotify = notificationHistoryBusiness.getNumberAreaRestrictionHasNotify();
        int numberAreaRestrictionHasNotifyNotResolveAndUsingRing = notificationHistoryBusiness.getNumberARHasNotifyNotResolveAndUsingRing();
        return new ResponseEntity<>(new AreaRestrictionCountResponse(HistoryErrorCode.SUCCESS, numberAreaRestrictionHasNotify, numberNotificationInDay, numberNotificationNotResolve, numberAreaRestrictionHasNotifyNotResolveAndUsingRing), HttpStatus.OK);
    }
}
