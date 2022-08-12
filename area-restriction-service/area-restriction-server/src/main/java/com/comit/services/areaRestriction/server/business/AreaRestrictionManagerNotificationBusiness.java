package com.comit.services.areaRestriction.server.business;

import com.comit.services.areaRestriction.client.request.ManagerTimeSkip;
import com.comit.services.areaRestriction.server.model.AreaRestrictionManagerNotification;

import java.util.List;

public interface AreaRestrictionManagerNotificationBusiness {
    List<AreaRestrictionManagerNotification> saveAreaManagerTimeList(List<ManagerTimeSkip> managerTimeSkips, Integer areaRestrictionId);

    List<AreaRestrictionManagerNotification> getAreaManagerTimeList(Integer areaRestrictionId);

    boolean deleteAreaRestrictionManagerNotificationList(Integer areaRestrictionId);

    boolean deleteARManagerNotificationOfEmployee(Integer employeeId);
}
