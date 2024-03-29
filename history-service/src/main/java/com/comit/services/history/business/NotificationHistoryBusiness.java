package com.comit.services.history.business;

import com.comit.services.history.controller.request.NotificationHistoryRequest;
import com.comit.services.history.model.dto.NotificationHistoryDto;
import com.comit.services.history.model.entity.NotificationHistory;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface NotificationHistoryBusiness {
    Page<NotificationHistory> getNotificationHistoryPage(String types, String employeeIds, String timeStart, String timeEnd, Integer locationId, int page, int size) throws ParseException;

    Page<NotificationHistory> getNotificationHistoryPage(boolean hasEmployee, String timeStart, String timeEnd, Integer locationId, int page, int size) throws ParseException;

    Page<NotificationHistory> getNotificationHistoryPage(String status, int page, int size) throws ParseException;

    Page<NotificationHistory> getNotificationHistoryPage(String types, String timeStart, String timeEnd, Integer locationId, String areaRestrictionIds, int page, int size) throws ParseException;

    List<NotificationHistoryDto> getAllNotificationHistory(List<NotificationHistory> content);

    NotificationHistoryDto saveNotificationHistory(NotificationHistoryRequest request);

    Page<NotificationHistory> getNotificationHistoryPage(String areaRestrictionIds, String timeStart, String timeEnd, String status, int page, int size);

    boolean updateStatusNotificationHistory(Integer id);

    int getNumberUserNotificationInCurrenDay();

    int getNumberLateInMonth();

    int getNumberNotificationInDay();

    int getNumberNotificationNotResolve();

    int getNumberAreaRestrictionHasNotify();

    int getNumberARHasNotifyNotResolveAndUsingRing();

    int getNumberNotificationOfAreaRestriction(Integer areaRestrictionId, String status);

    boolean hasHistory(Integer locationId, Integer employeeId, String timeStart, String timeEnd);

    int getNumberNotification(Integer locationId, Integer employeeId, String timeStart, String timeEnd);

    Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndCameraIdAndStatus(Integer areaRestrictionId, Integer cameraId, String status, String timeStart, String timeEnd, Boolean hasEmployee, int page, int size);

    Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndCameraId(Integer areaRestrictionId, Integer cameraId, String timeStart, String timeEnd, Boolean hasEmployee, int page, int size);

    Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndStatus(Integer areaRestrictionId, String status, String timeStart, String timeEnd, Boolean hasEmployee, int page, int size);

    Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionId(Integer areaRestrictionId, String timeStart, String timeEnd, Boolean hasEmployee, int page, int size);

    Page<NotificationHistory> getNotificationHistoryPageByCameraIdAndStatus(Integer cameraId, String status, String timeStart, String timeEnd, Boolean hasEmployee, int page, int size);

    Page<NotificationHistory> getNotificationHistoryPageByCameraId(Integer cameraId, String timeStart, String timeEnd, Boolean hasEmployee, int page, int size);

    Page<NotificationHistory> getNotificationHistoryPageByStatus(String status, String timeStart, String timeEnd, Boolean hasEmployee, int page, int size);

    Page<NotificationHistory> getNotificationHistoryPage(String timeStart, String timeEnd, Boolean hasEmployee, int page, int size);
}
