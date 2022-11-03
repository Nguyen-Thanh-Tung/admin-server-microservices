package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.model.entity.NotificationMethod;

public interface NotificationMethodServices {
    NotificationMethod saveNotificationMethod(NotificationMethod notificationMethod);

    NotificationMethod getNotification(int id);
}
