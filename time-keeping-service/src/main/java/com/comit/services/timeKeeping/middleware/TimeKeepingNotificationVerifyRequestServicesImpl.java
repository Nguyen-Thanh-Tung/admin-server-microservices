package com.comit.services.timeKeeping.middleware;

import com.comit.services.timeKeeping.constant.TimeKeepingNotificationErrorCode;
import com.comit.services.timeKeeping.controller.request.TimeKeepingNotificationRequest;
import com.comit.services.timeKeeping.exception.TimeKeepingCommonException;
import org.springframework.stereotype.Service;

@Service
public class TimeKeepingNotificationVerifyRequestServicesImpl implements TimeKeepingNotificationVerifyRequestServices {
    @Override
    public void verifyUpdateTimeKeepingNotificationRequest(TimeKeepingNotificationRequest request) {
        Integer lateTime = request.getLateTime();
        Integer lateInWeek = request.getLateInWeek();
        Integer lateInMonth = request.getLateInMonth();
        Integer lateInQuarter = request.getLateInQuarter();

        Boolean useOTT = request.getUseOTT();
        Boolean useEmail = request.getUseEmail();
        Boolean useScreen = request.getUseScreen();
        Boolean useRing = request.getUseRing();

        if (lateTime == null) {
            throw new TimeKeepingCommonException(TimeKeepingNotificationErrorCode.MISSING_LATE_TIME_FIELD);
        }

        if (lateInWeek == null) {
            throw new TimeKeepingCommonException(TimeKeepingNotificationErrorCode.MISSING_LATE_IN_WEEK_FIELD);
        }

        if (lateInMonth == null) {
            throw new TimeKeepingCommonException(TimeKeepingNotificationErrorCode.MISSING_LATE_IN_MONTH_FIELD);
        }

        if (lateInQuarter == null) {
            throw new TimeKeepingCommonException(TimeKeepingNotificationErrorCode.MISSING_LATE_IN_QUARTER_FIELD);
        }

        if (useOTT == null) {
            throw new TimeKeepingCommonException(TimeKeepingNotificationErrorCode.MISSING_USE_OTT_FIELD);
        }

        if (useEmail == null) {
            throw new TimeKeepingCommonException(TimeKeepingNotificationErrorCode.MISSING_USE_EMAIL_FIELD);
        }

        if (useScreen == null) {
            throw new TimeKeepingCommonException(TimeKeepingNotificationErrorCode.MISSING_USE_SCREEN_FIELD);
        }

        if (useRing == null) {
            throw new TimeKeepingCommonException(TimeKeepingNotificationErrorCode.MISSING_USE_RING_FIELD);
        }
    }
}
