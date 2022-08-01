package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.controller.request.ManagerTimeSkip;
import com.comit.services.areaRestriction.model.entity.AreaRestrictionManagerNotification;
import com.comit.services.areaRestriction.model.entity.Employee;
import com.comit.services.areaRestriction.model.entity.Location;
import com.comit.services.areaRestriction.service.AreaRestrictionManagerNotificationService;
import com.comit.services.areaRestriction.service.AreaRestrictionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AreaRestrictionManagerNotificationBusinessImpl implements AreaRestrictionManagerNotificationBusiness {

    @Autowired
    private AreaRestrictionManagerNotificationService areaRestrictionManagerNotificationService;
    @Autowired
    private AreaRestrictionServices areaRestrictionServices;

    @Override
    public List<AreaRestrictionManagerNotification> saveAreaManagerTimeList(List<ManagerTimeSkip> managerTimeSkips, Integer areaRestrictionId) {
        Location location = areaRestrictionServices.getLocationOfCurrentUser();
        List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications = new ArrayList<>();
        managerTimeSkips.forEach(managerTimeSkip -> {
            Employee manager = areaRestrictionServices.getEmployee(managerTimeSkip.getManagerId(), location.getId());
            if (manager != null) {
                AreaRestrictionManagerNotification areaRestrictionManagerNotification = new AreaRestrictionManagerNotification();

                areaRestrictionManagerNotification.setAreaRestrictionId(areaRestrictionId);
                areaRestrictionManagerNotification.setTimeSkip(managerTimeSkip.getTimeSkip());
                areaRestrictionManagerNotification.setManagerId(manager.getId());
                areaRestrictionManagerNotifications.add(areaRestrictionManagerNotification);
            }

        });
        return areaRestrictionManagerNotificationService.saveAllAreaManagerTime(areaRestrictionManagerNotifications);
    }

    @Override
    public boolean deleteAreaRestrictionManagerNotificationList(Integer areaRestrictionId) {
        return areaRestrictionManagerNotificationService.deleteAreaRestrictionManagerNotificationListOfAreaRestrictionNotification(areaRestrictionId);
    }
}
