package com.comit.services.history.controller;

import com.comit.services.history.business.InOutHistoryBusiness;
import com.comit.services.history.business.NotificationHistoryBusiness;
import com.comit.services.history.constant.Const;
import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.controller.response.BaseResponse;
import com.comit.services.history.controller.response.CountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/histories")
public class HistoryController {
    @Autowired
    InOutHistoryBusiness inOutHistoryBusiness;

    @Autowired
    NotificationHistoryBusiness notificationHistoryBusiness;

    @GetMapping(value = "/count")
    public ResponseEntity<BaseResponse> getTimeKeepingCount() {
        int numberCheckInCurrentDay = inOutHistoryBusiness.getNumberCheckInCurrentDay();
        int numberUserNotificationInCurrenDay = notificationHistoryBusiness.getNumberUserNotificationInCurrenDay();
        int numberUserLateInMonth = notificationHistoryBusiness.getNumberLateInMonth();
        return new ResponseEntity<>(new CountResponse(HistoryErrorCode.SUCCESS, numberCheckInCurrentDay, numberUserNotificationInCurrenDay, numberUserLateInMonth), HttpStatus.OK);
    }
}
