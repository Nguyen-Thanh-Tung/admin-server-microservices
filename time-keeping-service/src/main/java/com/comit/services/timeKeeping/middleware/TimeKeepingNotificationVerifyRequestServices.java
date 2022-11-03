package com.comit.services.timeKeeping.middleware;

import com.comit.services.timeKeeping.controller.request.TimeKeepingNotificationRequest;

public interface TimeKeepingNotificationVerifyRequestServices {
    void verifyUpdateTimeKeepingNotificationRequest(TimeKeepingNotificationRequest request);
}
