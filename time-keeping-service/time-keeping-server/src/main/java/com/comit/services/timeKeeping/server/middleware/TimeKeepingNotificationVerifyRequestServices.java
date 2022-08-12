package com.comit.services.timeKeeping.server.middleware;


import com.comit.services.timeKeeping.client.request.TimeKeepingNotificationRequest;

public interface TimeKeepingNotificationVerifyRequestServices {
    void verifyUpdateTimeKeepingNotificationRequest(TimeKeepingNotificationRequest request);
}
