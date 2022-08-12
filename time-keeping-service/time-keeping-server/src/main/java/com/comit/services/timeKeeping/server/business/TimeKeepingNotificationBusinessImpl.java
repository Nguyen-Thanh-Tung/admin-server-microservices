package com.comit.services.timeKeeping.server.business;

import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.timeKeeping.client.dto.NotificationMethodDto;
import com.comit.services.timeKeeping.client.dto.TimeKeepingNotificationDto;
import com.comit.services.timeKeeping.client.request.TimeKeepingNotificationRequest;
import com.comit.services.timeKeeping.server.constant.Const;
import com.comit.services.timeKeeping.server.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.server.constant.TimeKeepingNotificationErrorCode;
import com.comit.services.timeKeeping.server.exception.TimeKeepingCommonException;
import com.comit.services.timeKeeping.server.middleware.TimeKeepingNotificationVerifyRequestServices;
import com.comit.services.timeKeeping.server.model.NotificationMethod;
import com.comit.services.timeKeeping.server.model.TimeKeepingNotification;
import com.comit.services.timeKeeping.server.service.NotificationMethodServices;
import com.comit.services.timeKeeping.server.service.TimeKeepingNotificationServices;
import com.comit.services.timeKeeping.server.service.TimeKeepingServices;
import org.modelmapper.ModelMapper;
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

        LocationDto locationDto = timeKeepingServices.getLocationOfCurrentUser();

        TimeKeepingNotification timeKeepingNotification = timeKeepingNotificationServices.getTimeKeepingNotification(locationDto.getId());
        return convertTimeKeepingNotificationToDto(timeKeepingNotification);
    }

    @Override
    public TimeKeepingNotificationDto updateTimeKeepingNotification(int id, TimeKeepingNotificationRequest request) {
        timeKeepingVerifyRequestServices.verifyUpdateTimeKeepingNotificationRequest(request);

        // Is user and has role manage employee (Ex: Time keeping user)
        permissionManageTimeKeepingNotification();

        // Get time keeping notification
        LocationDto locationDto = timeKeepingServices.getLocationOfCurrentUser();
        TimeKeepingNotification timeKeepingNotification = timeKeepingNotificationServices.getTimeKeepingNotification(id, locationDto.getId());
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
        return convertTimeKeepingNotificationToDto(timeKeepingNotification);
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
        LocationDto locationDto = timeKeepingServices.getLocationOfCurrentUser();

        if (!timeKeepingNotificationServices.hasPermissionManageTimeKeepingNotification(locationDto != null ? locationDto.getType() : null)) {
            throw new TimeKeepingCommonException(TimeKeepingErrorCode.PERMISSION_DENIED);
        }
    }

    public TimeKeepingNotificationDto convertTimeKeepingNotificationToDto(TimeKeepingNotification timeKeepingNotification) {
        if (timeKeepingNotification == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        TimeKeepingNotificationDto timeKeepingNotificationDto = modelMapper.map(timeKeepingNotification, TimeKeepingNotificationDto.class);
        NotificationMethod notificationMethod = notificationMethodServices.getNotification(timeKeepingNotification.getNotificationMethodId());
        timeKeepingNotificationDto.setNotificationMethod(convertNotificationMethodToNotificationMethodDto(notificationMethod));
        return timeKeepingNotificationDto;
    }

    public NotificationMethodDto convertNotificationMethodToNotificationMethodDto(NotificationMethod notificationMethod) {
        if (notificationMethod == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(notificationMethod, NotificationMethodDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
