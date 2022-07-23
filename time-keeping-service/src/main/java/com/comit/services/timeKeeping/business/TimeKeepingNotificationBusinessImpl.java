package com.comit.services.timeKeeping.business;

import com.comit.services.timeKeeping.constant.Const;
import com.comit.services.timeKeeping.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.constant.TimeKeepingNotificationErrorCode;
import com.comit.services.timeKeeping.controller.request.TimeKeepingNotificationRequest;
import com.comit.services.timeKeeping.exception.TimeKeepingCommonException;
import com.comit.services.timeKeeping.middleware.TimeKeepingNotificationVerifyRequestServices;
import com.comit.services.timeKeeping.model.dto.TimeKeepingNotificationDto;
import com.comit.services.timeKeeping.model.entity.Location;
import com.comit.services.timeKeeping.model.entity.NotificationMethod;
import com.comit.services.timeKeeping.model.entity.TimeKeepingNotification;
import com.comit.services.timeKeeping.service.NotificationMethodServices;
import com.comit.services.timeKeeping.service.TimeKeepingNotificationServices;
import com.comit.services.timeKeeping.service.TimeKeepingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeKeepingNotificationBusinessImpl implements TimeKeepingNotificationBusiness {
    @Autowired
    private TimeKeepingNotificationVerifyRequestServices timeKeepingVerifyRequestServices;
    @Autowired
    private TimeKeepingNotificationServices timeKeepingNotificationServices;
    @Autowired
    private TimeKeepingServices timeKeepingServices;
    @Autowired
    private NotificationMethodServices notificationMethodServices;

    @Override
    public TimeKeepingNotificationDto getTimeKeepingNotification() {
        // Is user and has role manage employee (Ex: Time keeping user)
        permissionManageTimeKeepingNotification();

        Location location = timeKeepingServices.getLocationOfCurrentUser();

        TimeKeepingNotification timeKeepingNotification = timeKeepingNotificationServices.getTimeKeepingNotification(location.getId());
        return TimeKeepingNotificationDto.convertTimeKeepingNotificationToDto(timeKeepingNotification);
    }

    @Override
    public TimeKeepingNotificationDto updateTimeKeepingNotification(int id, TimeKeepingNotificationRequest request) {
        timeKeepingVerifyRequestServices.verifyUpdateTimeKeepingNotificationRequest(request);

        // Is user and has role manage employee (Ex: Time keeping user)
        permissionManageTimeKeepingNotification();

        // Get time keeping notification
        Location location = timeKeepingServices.getLocationOfCurrentUser();
        TimeKeepingNotification timeKeepingNotification = timeKeepingNotificationServices.getTimeKeepingNotification(id, location.getId());
        if (timeKeepingNotification == null) {
            throw new TimeKeepingCommonException(TimeKeepingNotificationErrorCode.TIME_KEEPING_NOTIFICATION_NOT_EXIST);
        }

        NotificationMethod notificationMethod = notificationMethodServices.getNotification(timeKeepingNotification.getNotificationMethodId());
        notificationMethod.setUseOTT(request.getUseOTT());
        notificationMethod.setUseEmail(request.getUseEmail());
        notificationMethod.setUseRing(request.getUseRing());
        notificationMethod.setUseScreen(request.getUseScreen());

        timeKeepingNotification.setLateTime(request.getLateTime());
        timeKeepingNotification.setLateInWeek(request.getLateInWeek());
        timeKeepingNotification.setLateInMonth(request.getLateInMonth());
        timeKeepingNotification.setLateInQuarter(request.getLateInQuarter());
        timeKeepingNotification.setStartDayOfWeek(request.getStartDayOfWeek());
        timeKeepingNotification.setEndDayOfWeek(request.getEndDayOfWeek());

        timeKeepingNotification.setNotificationMethodId(notificationMethod.getId());
        timeKeepingNotificationServices.saveTimeKeepingNotification(timeKeepingNotification);
        return TimeKeepingNotificationDto.convertTimeKeepingNotificationToDto(timeKeepingNotification);
    }

    @Override
    public boolean addTimeKeepingNotificationForLocation(Integer locationId) {
        //Add notification method for location
        NotificationMethod notificationMethod = new NotificationMethod();
        notificationMethod.setUseEmail(true);
        NotificationMethod newNotificationMethod = notificationMethodServices.saveNotificationMethod(notificationMethod);

        //Add time keeping notification setting for location (time keeping module)
        TimeKeepingNotification timeKeepingNotification = new TimeKeepingNotification();
        timeKeepingNotification.setLocationId(locationId);
        timeKeepingNotification.setLateTime(Const.LATE_TIME_NOTIFICATION);
        timeKeepingNotification.setLateInWeek(Const.LATE_TIME_IN_WEEK);
        timeKeepingNotification.setLateInMonth(Const.LATE_TIME_IN_MONTH);
        timeKeepingNotification.setLateInQuarter(Const.LATE_TIME_IN_QUARTER);
        timeKeepingNotification.setStartDayOfWeek(1); // Monday
        timeKeepingNotification.setEndDayOfWeek(5); // Friday
        timeKeepingNotification.setNotificationMethodId(newNotificationMethod.getId());
        return timeKeepingNotificationServices.saveTimeKeepingNotification(timeKeepingNotification);
    }

    @Override
    public boolean deleteTimeKeepingNotificationOfLocation(Integer locationId) {
        return timeKeepingNotificationServices.deleteTimeKeepingNotificationByLocationId(locationId);
    }

    private void permissionManageTimeKeepingNotification() {
        // Check role for employee
        Location location = timeKeepingServices.getLocationOfCurrentUser();

        if (!timeKeepingNotificationServices.hasPermissionManageTimeKeepingNotification(location != null ? location.getType() : null)) {
            throw new TimeKeepingCommonException(TimeKeepingErrorCode.PERMISSION_DENIED);
        }
    }
}
