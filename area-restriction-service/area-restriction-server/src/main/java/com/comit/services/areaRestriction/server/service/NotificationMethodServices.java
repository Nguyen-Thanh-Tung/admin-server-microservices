package com.comit.services.areaRestriction.server.service;

import com.comit.services.areaRestriction.server.model.NotificationMethod;

public interface NotificationMethodServices {
    NotificationMethod saveNotificationMethod(NotificationMethod notificationMethod);

    NotificationMethod getNotificationMethodOfAreaRestriction(int areaRestrictionId);
}
