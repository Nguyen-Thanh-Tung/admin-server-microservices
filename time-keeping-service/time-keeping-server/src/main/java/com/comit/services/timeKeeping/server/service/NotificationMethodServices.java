package com.comit.services.timeKeeping.server.service;

import com.comit.services.timeKeeping.server.model.NotificationMethod;

public interface NotificationMethodServices {
    NotificationMethod saveNotificationMethod(NotificationMethod notificationMethod);

    NotificationMethod getNotification(int id);
}
