package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.controller.request.ManagerTimeSkip;
import com.comit.services.areaRestriction.model.entity.AreaRestrictionManagerNotification;

import java.util.List;

public interface AreaRestrictionManagerNotificationBusiness {
    List<AreaRestrictionManagerNotification> saveAreaManagerTimeList(List<ManagerTimeSkip> managerTimeSkips, Integer areaRestrictionId);

    boolean deleteAreaRestrictionManagerNotificationList(Integer areaRestrictionId);
}
