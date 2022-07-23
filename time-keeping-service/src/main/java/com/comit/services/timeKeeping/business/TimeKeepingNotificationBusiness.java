package com.comit.services.timeKeeping.business;

import com.comit.services.timeKeeping.controller.request.TimeKeepingNotificationRequest;
import com.comit.services.timeKeeping.model.dto.TimeKeepingNotificationDto;

public interface TimeKeepingNotificationBusiness {
    TimeKeepingNotificationDto getTimeKeepingNotification();

    TimeKeepingNotificationDto updateTimeKeepingNotification(int id, TimeKeepingNotificationRequest request);

    boolean addTimeKeepingNotificationForLocation(Integer locationId);

    boolean deleteTimeKeepingNotificationOfLocation(Integer locationId);
}
