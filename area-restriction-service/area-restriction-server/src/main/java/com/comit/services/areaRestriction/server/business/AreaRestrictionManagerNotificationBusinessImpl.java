package com.comit.services.areaRestriction.server.business;

import com.comit.services.areaRestriction.client.request.ManagerTimeSkip;
import com.comit.services.areaRestriction.server.model.AreaRestrictionManagerNotification;
import com.comit.services.areaRestriction.server.service.AreaRestrictionManagerNotificationService;
import com.comit.services.areaRestriction.server.service.AreaRestrictionServices;
import com.comit.services.employee.client.dto.EmployeeDto;
import com.comit.services.location.client.dto.LocationDto;
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
        LocationDto locationDto = areaRestrictionServices.getLocationOfCurrentUser();
        List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications = new ArrayList<>();
        managerTimeSkips.forEach(managerTimeSkip -> {
            EmployeeDto managerDto = areaRestrictionServices.getEmployee(managerTimeSkip.getManagerId(), locationDto.getId());
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
