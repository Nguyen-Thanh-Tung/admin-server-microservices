package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.model.entity.NotificationMethod;

public interface NotificationMethodServices {
    NotificationMethod saveNotificationMethod(NotificationMethod notificationMethod);

    NotificationMethod getNotificationMethodOfAreaRestriction(int areaRestrictionId);
}
