package com.comit.services.timeKeeping.business;

import com.comit.services.timeKeeping.client.data.LocationDtoClient;
import com.comit.services.timeKeeping.constant.Const;
import com.comit.services.timeKeeping.constant.TimeKeepingNotificationErrorCode;
import com.comit.services.timeKeeping.controller.request.TimeKeepingNotificationRequest;
import com.comit.services.timeKeeping.exception.TimeKeepingCommonException;
import com.comit.services.timeKeeping.middleware.TimeKeepingNotificationVerifyRequestServices;
import com.comit.services.timeKeeping.model.dto.NotificationMethodDto;
import com.comit.services.timeKeeping.model.dto.TimeKeepingNotificationDto;
import com.comit.services.timeKeeping.model.entity.NotificationMethod;
import com.comit.services.timeKeeping.model.entity.TimeKeepingNotification;
import com.comit.services.timeKeeping.service.NotificationMethodServices;
import com.comit.services.timeKeeping.service.TimeKeepingNotificationServices;
import com.comit.services.timeKeeping.service.TimeKeepingServices;
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

        LocationDtoClient locationDtoClient = timeKeepingServices.getLocationOfCurrentUser();

        TimeKeepingNotification timeKeepingNotification = timeKeepingNotificationServices.getTimeKeepingNotification(locationDtoClient.getId());
        return convertTimeKeepingNotificationToDto(timeKeepingNotification);
    }

    @Override
    public TimeKeepingNotificationDto getTimeKeepingNotification(Integer locationId) {
        TimeKeepingNotification timeKeepingNotification = timeKeepingNotificationServices.getTimeKeepingNotification(locationId);
        return convertTimeKeepingNotificationToDto(timeKeepingNotification);
    }

    @Override
    public TimeKeepingNotificationDto updateTimeKeepingNotification(int id, TimeKeepingNotificationRequest request) {
        timeKeepingVerifyRequestServices.verifyUpdateTimeKeepingNotificationRequest(request);

        // Get time keeping notification
        LocationDtoClient locationDtoClient = timeKeepingServices.getLocationOfCurrentUser();
        TimeKeepingNotification timeKeepingNotification = timeKeepingNotificationServices.getTimeKeepingNotification(id, locationDtoClient.getId());
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

    public TimeKeepingNotificationDto convertTimeKeepingNotificationToDto(TimeKeepingNotification timeKeepingNotification) {
        if (timeKeepingNotification == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        TimeKeepingNotificationDto timeKeepingNotificationDto = modelMapper.map(timeKeepingNotification, TimeKeepingNotificationDto.class);
        NotificationMethod notificationMethod = notificationMethodServices.getNotification(timeKeepingNotification.getNotificationMethodId());
        timeKeepingNotificationDto.setNotificationMethod(NotificationMethodDto.convertNotificationMethodToNotificationMethodDto(notificationMethod));
        return timeKeepingNotificationDto;
    }
}
