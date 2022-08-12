package com.comit.services.timeKeeping.server.business;

import com.comit.services.timeKeeping.client.dto.TimeKeepingNotificationDto;
import com.comit.services.timeKeeping.client.request.TimeKeepingNotificationRequest;

public interface TimeKeepingNotificationBusiness {
    TimeKeepingNotificationDto getTimeKeepingNotification();

    TimeKeepingNotificationDto updateTimeKeepingNotification(int id, TimeKeepingNotificationRequest request);

    boolean addTimeKeepingNotificationForLocation(Integer locationId);

    boolean deleteTimeKeepingNotificationOfLocation(Integer locationId);
}
