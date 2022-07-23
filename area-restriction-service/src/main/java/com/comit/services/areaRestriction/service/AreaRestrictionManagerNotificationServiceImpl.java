package com.comit.services.areaRestriction.service;

import com.comit.services.areaRestriction.model.entity.AreaRestrictionManagerNotification;
import com.comit.services.areaRestriction.repository.AreaRestrictionManagerNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AreaRestrictionManagerNotificationServiceImpl implements AreaRestrictionManagerNotificationService {
    @Autowired
    private AreaRestrictionManagerNotificationRepository areaRestrictionManagerNotificationRepository;

    @Override
    public List<AreaRestrictionManagerNotification> saveAllAreaManagerTime(List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications) {
        return areaRestrictionManagerNotificationRepository.saveAll(areaRestrictionManagerNotifications);
    }

    @Override
    public boolean deleteAreaRestrictionManagerNotificationListOfAreaRestrictionNotification(Integer areaRestrictionId) {
        try {
            areaRestrictionManagerNotificationRepository.deleteAllByAreaRestrictionId(areaRestrictionId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deleteAreaRestrictionManagerNotificationListOfEmployee(Integer employeeId) {
        try {
            areaRestrictionManagerNotificationRepository.deleteAllByManagerId(employeeId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
