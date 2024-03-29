package com.comit.services.history.business;

import com.comit.services.history.client.data.LocationDtoClient;
import com.comit.services.history.controller.request.NotificationHistoryRequest;
import com.comit.services.history.model.dto.NotificationHistoryDto;
import com.comit.services.history.model.entity.NotificationHistory;
import com.comit.services.history.service.HistoryServices;
import com.comit.services.history.service.NotificationHistoryServices;
import com.comit.services.history.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationHistoryBusinessImpl implements NotificationHistoryBusiness {

    @Autowired
    private HistoryBusiness historyBusiness;
    @Autowired
    private NotificationHistoryServices notificationHistoryServices;
    @Autowired
    private HistoryServices historyServices;

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(String typeStrs, String employeeIdStrs, String timeStartStr, String timeEndStr, Integer locationId, int page, int size) throws ParseException {
        // Is user and has role manage employee (Ex: Time keeping user)
        LocationDtoClient locationDtoClient;
        if (locationId == null) {
            locationDtoClient = historyServices.getLocationOfCurrentUser();
        } else {
            locationDtoClient = historyServices.getLocation(locationId);
        }

        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);

        if (typeStrs == null && employeeIdStrs == null) {
            return notificationHistoryServices.getNotificationHistoryPage(locationDtoClient.getId(), timeStart, timeEnd, paging);
        } else if (employeeIdStrs == null) {
            String[] types = typeStrs.split(",");

            return notificationHistoryServices.getNotificationHistoryPageType(locationDtoClient.getId(), List.of(types), timeStart, timeEnd, paging);
        } else if (typeStrs == null) {
            String[] tmp = employeeIdStrs.split(",");
            List<Integer> employeeIds = new ArrayList<>();
            for (String employeeId : tmp) {
                employeeIds.add(Integer.parseInt(employeeId));
            }
            return notificationHistoryServices.getNotificationHistoryPage(locationDtoClient.getId(), employeeIds, timeStart, timeEnd, paging);
        } else {
            String[] types = typeStrs.split(",");
            String[] tmp = employeeIdStrs.split(",");
            List<Integer> employeeIds = new ArrayList<>();
            for (String employeeId : tmp) {
                employeeIds.add(Integer.parseInt(employeeId));
            }
            return notificationHistoryServices.getNotificationHistoryPageType(locationDtoClient.getId(), List.of(types), employeeIds, timeStart, timeEnd, paging);
        }
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(boolean hasEmployee, String timeStartStr, String timeEndStr, Integer locationId, int page, int size) throws ParseException {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);

        return notificationHistoryServices.getNotificationHistoryPage(locationDtoClient.getId(), hasEmployee, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(String status, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        return notificationHistoryServices.getNotificationHistoryPage(locationDtoClient.getId(), status, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(String typeStrs, String timeStartStr, String timeEndStr, Integer locationId, String areaRestrictionIdStrs, int page, int size) throws ParseException {
        // Is user and has role manage employee (Ex: Time keeping user)
        LocationDtoClient locationDtoClient;
        if (locationId == null) {
            locationDtoClient = historyServices.getLocationOfCurrentUser();
        } else {
            locationDtoClient = historyServices.getLocation(locationId);
        }

        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);

        if (typeStrs == null && areaRestrictionIdStrs == null) {
            return notificationHistoryServices.getNotificationHistoryPage(locationDtoClient.getId(), timeStart, timeEnd, paging);
        } else if (areaRestrictionIdStrs == null) {
            String[] types = typeStrs.split(",");

            return notificationHistoryServices.getNotificationHistoryPageType(locationDtoClient.getId(), List.of(types), timeStart, timeEnd, paging);
        } else if (typeStrs == null) {
            String[] tmp = areaRestrictionIdStrs.split(",");
            List<Integer> areaRestrictionIds = new ArrayList<>();
            for (String areaRestrictionId : tmp) {
                areaRestrictionIds.add(Integer.parseInt(areaRestrictionId));
            }
            return notificationHistoryServices.getNotificationHistoryPageAreaRestriction(locationDtoClient.getId(), areaRestrictionIds, timeStart, timeEnd, paging);
        } else {
            String[] types = typeStrs.split(",");
            String[] tmp = areaRestrictionIdStrs.split(",");
            List<Integer> areaRestrictionIds = new ArrayList<>();
            for (String areaRestrictionId : tmp) {
                areaRestrictionIds.add(Integer.parseInt(areaRestrictionId));
            }
            return notificationHistoryServices.getNotificationHistoryPageTypeAreaRestriction(locationDtoClient.getId(), List.of(types), areaRestrictionIds, timeStart, timeEnd, paging);
        }
    }


    @Override
    public List<NotificationHistoryDto> getAllNotificationHistory(List<NotificationHistory> notificationHistories) {
        List<NotificationHistoryDto> notificationHistoryDtos = new ArrayList<>();
        notificationHistories.forEach(notificationHistory -> {
            notificationHistoryDtos.add(historyBusiness.convertNotificationHistoryToNotificationHistoryDto(notificationHistory));
        });
        return notificationHistoryDtos;
    }

    @Override
    public NotificationHistoryDto saveNotificationHistory(NotificationHistoryRequest request) {
        NotificationHistory notificationHistory = new NotificationHistory();
        notificationHistory.setCameraId(request.getCameraId());
        notificationHistory.setEmployeeId(request.getEmployeeId());
        notificationHistory.setTime(TimeUtil.stringDateToDate(request.getTime()));
        notificationHistory.setType(request.getType());
        notificationHistory.setImageId(request.getImageId());
        notificationHistory.setAreaRestrictionId(request.getAreaRestrictionId());
        notificationHistory.setLocationId(request.getLocationId());
        notificationHistory.setStatus(request.getStatus());
        NotificationHistory newNotificationHistory = notificationHistoryServices.saveNotificationHistory(notificationHistory);
        return historyBusiness.convertNotificationHistoryToNotificationHistoryDto(newNotificationHistory);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(String areaRestrictionIdStrs, String timeStartStr, String timeEndStr, String status, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);
        String[] areaRestrictionIds = areaRestrictionIdStrs.split(",");
        List<Integer> areaRestrictions = new ArrayList<>();
        for (String areaRestrictionId : areaRestrictionIds) {
            areaRestrictions.add(Integer.parseInt(areaRestrictionId));
        }
        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);
        return notificationHistoryServices.getNotificationHistoryPage(locationDtoClient.getId(), areaRestrictions, timeStart, timeEnd, status, paging);
    }

    @Override
    public boolean updateStatusNotificationHistory(Integer id) {
        return notificationHistoryServices.updateStatusNotificationHistory(id);
    }

    @Override
    public int getNumberUserNotificationInCurrenDay() {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberUserNotificationInDay(locationDtoClient.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberLateInMonth() {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getFirstDayOfCurrentMonth();
        Date timeEnd = TimeUtil.getLastDayOfCurrentMonth();
        return notificationHistoryServices.getNumberLateInMonth(locationDtoClient.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberNotificationInDay() {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberNotificationInDay(locationDtoClient.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberNotificationNotResolve() {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberNotificationNotResolve(locationDtoClient.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberAreaRestrictionHasNotify() {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberAreaRestrictionHasNotify(locationDtoClient.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberARHasNotifyNotResolveAndUsingRing() {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberARHasNotifyNotResolveAndUsingRing(locationDtoClient.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberNotificationOfAreaRestriction(Integer areaRestrictionId, String status) {
        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");

        return notificationHistoryServices.getNumberNotificationOfAreaRestriction(areaRestrictionId, timeStart, timeEnd, status);
    }

    @Override
    public boolean hasHistory(Integer locationId, Integer employeeId, String timeStartStr, String timeEndStr) {
        Date timeStart = TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = TimeUtil.stringDateToDate(timeEndStr);

        return notificationHistoryServices.hasHistory(locationId, employeeId, timeStart, timeEnd);
    }

    @Override
    public int getNumberNotification(Integer locationId, Integer employeeId, String timeStartStr, String timeEndStr) {
        Date timeStart = TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = TimeUtil.stringDateToDate(timeEndStr);

        return notificationHistoryServices.getNumberNotification(locationId, employeeId, timeStart, timeEnd);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndCameraIdAndStatus(Integer areaRestrictionId, Integer cameraId, String status, String timeStartStr, String timeEndStr, Boolean hasEmployee, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);
        return notificationHistoryServices.getNotificationHistoryPageByAreaRestrictionIdAndCameraIdAndStatus(locationDtoClient.getId(), areaRestrictionId, cameraId, status, timeStart, timeEnd, hasEmployee, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndCameraId(Integer areaRestrictionId, Integer cameraId, String timeStartStr, String timeEndStr, Boolean hasEmployee, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);
        return notificationHistoryServices.getNotificationHistoryPageByAreaRestrictionIdAndCameraId(locationDtoClient.getId(), areaRestrictionId, cameraId, timeStart, timeEnd, hasEmployee, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndStatus(Integer areaRestrictionId, String status, String timeStartStr, String timeEndStr, Boolean hasEmployee, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);
        return notificationHistoryServices.getNotificationHistoryPageByAreaRestrictionIdAndStatus(locationDtoClient.getId(), areaRestrictionId, status, timeStart, timeEnd, hasEmployee, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionId(Integer areaRestrictionId, String timeStartStr, String timeEndStr, Boolean hasEmployee, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);
        return notificationHistoryServices.getNotificationHistoryPageByAreaRestrictionId(locationDtoClient.getId(), areaRestrictionId, timeStart, timeEnd, hasEmployee, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByCameraIdAndStatus(Integer cameraId, String status, String timeStartStr, String timeEndStr, Boolean hasEmployee, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);
        return notificationHistoryServices.getNotificationHistoryPageByCameraIdAndStatus(locationDtoClient.getId(), cameraId, status, timeStart, timeEnd, hasEmployee, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByCameraId(Integer cameraId, String timeStartStr, String timeEndStr, Boolean hasEmployee, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);
        return notificationHistoryServices.getNotificationHistoryPageByCameraId(locationDtoClient.getId(), cameraId, timeStart, timeEnd, hasEmployee, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByStatus(String status, String timeStartStr, String timeEndStr, Boolean hasEmployee, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);
        return notificationHistoryServices.getNotificationHistoryPageByStatus(locationDtoClient.getId(), status, timeStart, timeEnd, hasEmployee, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(String timeStartStr, String timeEndStr, Boolean hasEmployee, int page, int size) {
        LocationDtoClient locationDtoClient = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);
        return notificationHistoryServices.getNotificationHistoryPage(locationDtoClient.getId(), timeStart, timeEnd, hasEmployee, paging);
    }
}
