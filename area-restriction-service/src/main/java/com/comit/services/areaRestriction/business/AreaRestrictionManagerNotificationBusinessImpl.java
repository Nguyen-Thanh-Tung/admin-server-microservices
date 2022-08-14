package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.client.data.EmployeeDtoClient;
import com.comit.services.areaRestriction.client.data.LocationDtoClient;
import com.comit.services.areaRestriction.controller.request.ManagerTimeSkip;
import com.comit.services.areaRestriction.model.entity.AreaRestrictionManagerNotification;
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
        LocationDtoClient locationDtoClient = areaRestrictionServices.getLocationOfCurrentUser();
        List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications = new ArrayList<>();
        managerTimeSkips.forEach(managerTimeSkip -> {
            EmployeeDtoClient managerDto = areaRestrictionServices.getEmployee(managerTimeSkip.getManagerId(), locationDtoClient.getId());
            if (managerDto != null) {
                AreaRestrictionManagerNotification areaRestrictionManagerNotification = new AreaRestrictionManagerNotification();

                areaRestrictionManagerNotification.setAreaRestrictionId(areaRestrictionId);
                areaRestrictionManagerNotification.setTimeSkip(managerTimeSkip.getTimeSkip());
                areaRestrictionManagerNotification.setManagerId(managerDto.getId());
                areaRestrictionManagerNotifications.add(areaRestrictionManagerNotification);
            }

        });
        return areaRestrictionManagerNotificationService.saveAllAreaManagerTime(areaRestrictionManagerNotifications);
    }

    @Override
    public List<AreaRestrictionManagerNotification> getAreaManagerTimeList(Integer areaRestrictionId) {
        return areaRestrictionManagerNotificationService.getAreaRestrictionManagerNotifications(areaRestrictionId);
    }

    @Override
    public boolean deleteAreaRestrictionManagerNotificationList(Integer areaRestrictionId) {
        return areaRestrictionManagerNotificationService.deleteAreaRestrictionManagerNotificationListOfAreaRestrictionNotification(areaRestrictionId);
    }

    @Override
    public boolean deleteARManagerNotificationOfEmployee(Integer employeeId) {
        return areaRestrictionManagerNotificationService.deleteAreaRestrictionManagerNotificationListOfEmployee(employeeId);
    }
}
