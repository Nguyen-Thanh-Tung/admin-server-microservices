package com.comit.services.areaRestriction.business;

import com.comit.services.areaRestriction.client.data.EmployeeDtoClient;
import com.comit.services.areaRestriction.client.data.LocationDtoClient;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.constant.Const;
import com.comit.services.areaRestriction.controller.request.ManagerTimeSkip;
import com.comit.services.areaRestriction.exception.AreaRestrictionCommonException;
import com.comit.services.areaRestriction.model.entity.AreaRestrictionManagerNotification;
import com.comit.services.areaRestriction.service.AreaRestrictionManagerNotificationService;
import com.comit.services.areaRestriction.service.AreaRestrictionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AreaRestrictionManagerNotificationBusinessImpl implements AreaRestrictionManagerNotificationBusiness {

    @Autowired
    private AreaRestrictionManagerNotificationService areaRestrictionManagerNotificationService;
    @Autowired
    private AreaRestrictionServices areaRestrictionServices;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Value("${app.internalToken}")
    private String internalToken;

    @Override
    public List<AreaRestrictionManagerNotification> saveAreaManagerTimeList(List<ManagerTimeSkip> managerTimeSkips, Integer areaRestrictionId) {
        List<AreaRestrictionManagerNotification> areaRestrictionManagerNotifications = new ArrayList<>();
        managerTimeSkips.forEach(managerTimeSkip -> {
            EmployeeDtoClient managerDto = areaRestrictionServices.getEmployee(managerTimeSkip.getManagerId());
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
        if (!isInternalFeature()) throw new AreaRestrictionCommonException(AreaRestrictionErrorCode.PERMISSION_DENIED);
        return areaRestrictionManagerNotificationService.deleteAreaRestrictionManagerNotificationListOfEmployee(employeeId);
    }

    public boolean isInternalFeature() {
        return Objects.equals(httpServletRequest.getHeader("token"), internalToken);
    }
}
