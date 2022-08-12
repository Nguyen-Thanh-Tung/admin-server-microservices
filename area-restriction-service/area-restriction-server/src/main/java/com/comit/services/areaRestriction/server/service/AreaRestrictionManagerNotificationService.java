package com.comit.services.areaRestriction.server.service;

import com.comit.services.areaRestriction.server.model.AreaRestrictionManagerNotification;

import java.util.List;

public interface AreaRestrictionManagerNotificationService {
    List<AreaRestrictionManagerNotification> saveAllAreaManagerTime(List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications);

    boolean deleteAreaRestrictionManagerNotificationListOfAreaRestrictionNotification(Integer areaRestrictionId);

    boolean deleteAreaRestrictionManagerNotificationListOfEmployee(Integer employeeId);

    List<AreaRestrictionManagerNotification> getAreaRestrictionManagerNotifications(Integer areaRestrictionId);
}
