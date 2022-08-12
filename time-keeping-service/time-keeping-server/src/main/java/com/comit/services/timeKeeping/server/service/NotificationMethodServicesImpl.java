package com.comit.services.timeKeeping.server.service;

import com.comit.services.timeKeeping.server.model.NotificationMethod;
import com.comit.services.timeKeeping.server.repository.NotificationMethodRepository;
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
