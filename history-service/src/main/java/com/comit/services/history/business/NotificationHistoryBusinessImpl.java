package com.comit.services.history.business;

import com.comit.services.history.client.data.*;
import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.controller.request.NotificationHistoryRequest;
import com.comit.services.history.exception.RestApiException;
import com.comit.services.history.model.dto.NotificationHistoryDto;
import com.comit.services.history.model.entity.NotificationHistory;
import com.comit.services.history.service.HistoryServices;
import com.comit.services.history.service.NotificationHistoryServices;
import com.comit.services.history.util.TimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NotificationHistoryBusinessImpl implements NotificationHistoryBusiness {

    @Autowired
    private NotificationHistoryServices notificationHistoryServices;
    @Autowired
    private HistoryServices historyServices;

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(String typeStrs, String employeeIdStrs, String timeStartStr, String timeEndStr, Integer locationId, int page, int size) throws ParseException {
        // Is user and has role manage employee (Ex: Time keeping user)
        LocationDto locationDto;
        if (locationId == null) {
            permissionManageNotificationHistory();
            locationDto = historyServices.getLocationOfCurrentUser();
        } else {
            locationDto = historyServices.getLocation(locationId);
        }

        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);

        if (typeStrs == null && employeeIdStrs == null) {
            return notificationHistoryServices.getNotificationHistoryPage(locationDto.getId(), timeStart, timeEnd, paging);
        } else if (employeeIdStrs == null) {
            String[] types = typeStrs.split(",");

            return notificationHistoryServices.getNotificationHistoryPageType(locationDto.getId(), List.of(types), timeStart, timeEnd, paging);
        } else if (typeStrs == null) {
            String[] tmp = employeeIdStrs.split(",");
            List<Integer> employeeIds = new ArrayList<>();
            for (String employeeId : tmp) {
                employeeIds.add(Integer.parseInt(employeeId));
            }
            return notificationHistoryServices.getNotificationHistoryPage(locationDto.getId(), employeeIds, timeStart, timeEnd, paging);
        } else {
            String[] types = typeStrs.split(",");
            String[] tmp = employeeIdStrs.split(",");
            List<Integer> employeeIds = new ArrayList<>();
            for (String employeeId : tmp) {
                employeeIds.add(Integer.parseInt(employeeId));
            }
            return notificationHistoryServices.getNotificationHistoryPageType(locationDto.getId(), List.of(types), employeeIds, timeStart, timeEnd, paging);
        }
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(boolean hasEmployee, String timeStartStr, String timeEndStr, Integer locationId, int page, int size) throws ParseException {
        permissionManageNotificationHistory();
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);

        return notificationHistoryServices.getNotificationHistoryPage(locationDto.getId(), hasEmployee, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(String status, int page, int size) throws ParseException {
        permissionManageNotificationHistory();
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);

        return notificationHistoryServices.getNotificationHistoryPage(locationDto.getId(), status, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(String typeStrs, String timeStartStr, String timeEndStr, Integer locationId, String areaRestrictionIdStrs, int page, int size) throws ParseException {
        // Is user and has role manage employee (Ex: Time keeping user)
        LocationDto locationDto;
        if (locationId == null) {
            permissionManageNotificationHistory();
            locationDto = historyServices.getLocationOfCurrentUser();
        } else {
            locationDto = historyServices.getLocation(locationId);
        }

        Pageable paging = PageRequest.of(page, size);

        // If param not have timeStart and timeEnd then set default
        Date timeStart = timeStartStr == null ? TimeUtil.stringDateToDate("01/01/1970 00:00:00") : TimeUtil.stringDateToDate(timeStartStr);
        Date timeEnd = timeEndStr == null ? new Date() : TimeUtil.stringDateToDate(timeEndStr);

        if (typeStrs == null && areaRestrictionIdStrs == null) {
            return notificationHistoryServices.getNotificationHistoryPage(locationDto.getId(), timeStart, timeEnd, paging);
        } else if (areaRestrictionIdStrs == null) {
            String[] types = typeStrs.split(",");

            return notificationHistoryServices.getNotificationHistoryPageType(locationDto.getId(), List.of(types), timeStart, timeEnd, paging);
        } else if (typeStrs == null) {
            String[] tmp = areaRestrictionIdStrs.split(",");
            List<Integer> areaRestrictionIds = new ArrayList<>();
            for (String areaRestrictionId : tmp) {
                areaRestrictionIds.add(Integer.parseInt(areaRestrictionId));
            }
            return notificationHistoryServices.getNotificationHistoryPageAreaRestriction(locationDto.getId(), areaRestrictionIds, timeStart, timeEnd, paging);
        } else {
            String[] types = typeStrs.split(",");
            String[] tmp = areaRestrictionIdStrs.split(",");
            List<Integer> areaRestrictionIds = new ArrayList<>();
            for (String areaRestrictionId : tmp) {
                areaRestrictionIds.add(Integer.parseInt(areaRestrictionId));
            }
            return notificationHistoryServices.getNotificationHistoryPageTypeAreaRestriction(locationDto.getId(), List.of(types), areaRestrictionIds, timeStart, timeEnd, paging);
        }
    }


    @Override
    public List<NotificationHistoryDto> getAllNotificationHistory(List<NotificationHistory> notificationHistories) {
        List<NotificationHistoryDto> notificationHistoryDtos = new ArrayList<>();
        notificationHistories.forEach(notificationHistory -> {
            notificationHistoryDtos.add(convertNotificationHistoryToNotificationHistoryDto(notificationHistory));
        });
        return notificationHistoryDtos;
    }

    @Override
    public NotificationHistoryDto saveNotificationHistory(NotificationHistoryRequest request) throws ParseException {
        NotificationHistory notificationHistory = new NotificationHistory();
        notificationHistory.setCameraId(request.getCameraId());
        notificationHistory.setEmployeeId(request.getEmployeeId());
        notificationHistory.setTime(TimeUtil.stringDateToDate(request.getTime()));
        notificationHistory.setType(request.getType());
        notificationHistory.setImageId(request.getImageId());
        NotificationHistory newNotificationHistory = notificationHistoryServices.saveNotificationHistory(notificationHistory);
        return convertNotificationHistoryToNotificationHistoryDto(newNotificationHistory);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(String areaRestrictionIdStrs, String status, int page, int size) {
        permissionManageNotificationHistory();
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();
        Pageable paging = PageRequest.of(page, size);
        String[] areaRestrictionIds = areaRestrictionIdStrs.split(",");
        List<Integer> areaRestrictions = new ArrayList<>();
        for (String areaRestrictionId : areaRestrictionIds) {
            areaRestrictions.add(Integer.parseInt(areaRestrictionId));
        }
        return notificationHistoryServices.getNotificationHistoryPage(locationDto.getId(), areaRestrictions, status, paging);
    }

    @Override
    public boolean updateStatusNotificationHistory(Integer id) {
        permissionManageNotificationHistory();
        return notificationHistoryServices.updateStatusNotificationHistory(id);
    }

    @Override
    public int getNumberUserNotificationInCurrenDay() {
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberUserNotificationInDay(locationDto.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberLateInMonth() {
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getFirstDayOfCurrentMonth();
        Date timeEnd = TimeUtil.getLastDayOfCurrentMonth();
        return notificationHistoryServices.getNumberLateInMonth(locationDto.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberNotificationInDay() {
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberNotificationInDay(locationDto.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberNotificationNotResolve() {
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberNotificationNotResolve(locationDto.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberAreaRestrictionHasNotify() {
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberAreaRestrictionHasNotify(locationDto.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberARHasNotifyNotResolveAndUsingRing() {
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();

        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");
        return notificationHistoryServices.getNumberARHasNotifyNotResolveAndUsingRing(locationDto.getId(), timeStart, timeEnd);
    }

    @Override
    public int getNumberNotificationOfAreaRestriction(Integer areaRestrictionId) {
        // If param not have timeStart and timeEnd then set default
        Date timeStart = TimeUtil.getDateTimeFromTimeString("00:00:00");
        Date timeEnd = TimeUtil.getDateTimeFromTimeString("23:59:59");

        return notificationHistoryServices.getNumberNotificationOfAreaRestriction(areaRestrictionId, timeStart, timeEnd);
    }

    private void permissionManageNotificationHistory() {
        // Check role for employee
        LocationDto locationDto = historyServices.getLocationOfCurrentUser();

        if (!notificationHistoryServices.hasPermissionManageNotificationHistory(locationDto != null ? locationDto.getType() : null)) {
            throw new RestApiException(HistoryErrorCode.PERMISSION_DENIED);
        }
    }

    public NotificationHistoryDto convertNotificationHistoryToNotificationHistoryDto(NotificationHistory notificationHistory) {
        if (notificationHistory == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            NotificationHistoryDto notificationHistoryDto = modelMapper.map(notificationHistory, NotificationHistoryDto.class);
            EmployeeDto employeeDto = historyServices.getEmployee(notificationHistory.getEmployeeId());
            if (employeeDto != null) {
                notificationHistoryDto.setEmployee(employeeDto);
            }

            CameraDto cameraDto = historyServices.getCamera(notificationHistory.getCameraId());
            if (cameraDto != null) {
                if (cameraDto.getAreaRestriction() != null) {
                    AreaRestrictionDto areaRestrictionDto = cameraDto.getAreaRestriction();
                    cameraDto.setAreaRestriction(areaRestrictionDto);
                    NotificationMethodDto notificationMethodDto = historyServices.getNotificationMethodOfAreaRestriction(areaRestrictionDto.getId());
                    notificationHistoryDto.setNotificationMethod(notificationMethodDto);

                }
                notificationHistoryDto.setCamera(cameraDto);
            }

            MetadataDto metadataDto = historyServices.getMetadata(notificationHistory.getImageId());
            if (metadataDto != null) {
                notificationHistoryDto.setImage(metadataDto);
            }

            return notificationHistoryDto;
        } catch (Exception e) {
            return null;
        }
    }
}
