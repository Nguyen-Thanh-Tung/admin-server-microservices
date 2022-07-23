package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.model.entity.NotificationMethod;
import com.comit.services.timeKeeping.repository.NotificationMethodRepository;
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
    public NotificationMethod getNotification(int id) {
        return notificationMethodRepository.findById(id);
    }
}
