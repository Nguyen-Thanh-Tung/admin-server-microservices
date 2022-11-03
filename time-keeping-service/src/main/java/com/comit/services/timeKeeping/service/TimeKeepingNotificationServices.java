package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.model.entity.TimeKeepingNotification;

public interface TimeKeepingNotificationServices {
    TimeKeepingNotification getTimeKeepingNotification(int id, Integer locationId);

    TimeKeepingNotification getTimeKeepingNotification(Integer locationId);

    boolean saveTimeKeepingNotification(TimeKeepingNotification timeKeepingNotification);

    boolean deleteTimeKeepingNotificationByLocationId(Integer locationId);
}
