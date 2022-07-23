package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.model.entity.NotificationMethod;
import com.comit.services.areaRestriction.repository.NotificationMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationMethodServicesImpl implements NotificationMethodServices {
    @Autowired
    NotificationMethodRepository notificationMethodRepository;

    @Override
    public NotificationMethod saveNotificationMethod(NotificationMethod notificationMethod) {
        return notificationMethodRepository.save(notificationMethod);
    }

    @Override
    public NotificationMethod getNotificationMethodOfAreaRestriction(int areaRestrictionId) {
        return notificationMethodRepository.findByAreaRestrictionId(areaRestrictionId);
    }
}
