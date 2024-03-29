package com.comit.services.timeKeeping.service;

import com.comit.services.timeKeeping.model.entity.TimeKeepingNotification;
import com.comit.services.timeKeeping.repository.TimeKeepingNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TimeKeepingNotificationServicesImpl implements TimeKeepingNotificationServices {
    @Autowired
    private TimeKeepingNotificationRepository timeKeepingNotificationRepository;

    @Override
    public TimeKeepingNotification getTimeKeepingNotification(int id, Integer locationId) {
        return timeKeepingNotificationRepository.findByIdAndLocationId(id, locationId);
    }

    @Override
    public TimeKeepingNotification getTimeKeepingNotification(Integer locationId) {
        return timeKeepingNotificationRepository.findByLocationId(locationId);
    }

    @Override
    public boolean saveTimeKeepingNotification(TimeKeepingNotification timeKeepingNotification) {
        try {
            timeKeepingNotificationRepository.save(timeKeepingNotification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteTimeKeepingNotificationByLocationId(Integer locationId) {
        try {
            timeKeepingNotificationRepository.deleteByLocationId(locationId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
