package com.comit.services.timeKeeping.server.service;

import com.comit.services.timeKeeping.server.model.TimeKeepingNotification;

public interface TimeKeepingNotificationServices {
    TimeKeepingNotification getTimeKeepingNotification(int id, Integer locationId);

    TimeKeepingNotification getTimeKeepingNotification(Integer locationId);

    boolean saveTimeKeepingNotification(TimeKeepingNotification timeKeepingNotification);

    boolean hasPermissionManageTimeKeepingNotification(String locationType);

    boolean deleteTimeKeepingNotificationByLocationId(Integer locationId);
}
