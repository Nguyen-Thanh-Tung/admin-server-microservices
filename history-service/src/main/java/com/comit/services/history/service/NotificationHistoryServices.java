package com.comit.services.history.service;

import com.comit.services.history.model.entity.NotificationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface NotificationHistoryServices {
    Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageType(Integer locationId, List<String> types, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, List<Integer> employeeId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, boolean hasEmployee, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageAreaRestriction(Integer locationId, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageType(Integer locationId, List<String> types, List<Integer> employeeIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageTypeAreaRestriction(Integer locationId, List<String> types, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, String status, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, String status, Pageable paging);

    NotificationHistory saveNotificationHistory(NotificationHistory notificationHistory);

    int getNumberNotificationInDay(Integer locationId, Date timeStart, Date timeEnd);

    int getNumberNotificationNotResolve(Integer locationId, Date timeStart, Date timeEnd);

    int getNumberAreaRestrictionHasNotify(Integer locationId, Date timeStart, Date timeEnd);

    int getNumberARHasNotifyNotResolveAndUsingRing(Integer locationId, Date timeStart, Date timeEnd);

    int getNumberNotificationOfAreaRestriction(Integer areaRestrictionId, Date startDay, Date now, String status);

    boolean updateStatusNotificationHistory(int id);

    int getNumberUserNotificationInDay(Integer locationId, Date timeStart, Date timeEnd);

    int getNumberLateInMonth(Integer locationId, Date timeStart, Date timeEnd);

    boolean hasHistory(Integer locationId, Integer employeeId, Date timeStart, Date timeEnd);

    int getNumberNotification(Integer locationId, Integer employeeId, Date timeStart, Date timeEnd);

    Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndCameraIdAndStatus(Integer id, Integer areaRestrictionId, Integer cameraId, String status, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndCameraId(Integer id, Integer areaRestrictionId, Integer cameraId, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndStatus(Integer id, Integer areaRestrictionId, String status, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionId(Integer id, Integer areaRestrictionId, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageByCameraIdAndStatus(Integer id, Integer cameraId, String status, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageByCameraId(Integer id, Integer cameraId, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPageByStatus(Integer id, String status, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging);

    Page<NotificationHistory> getNotificationHistoryPage(Integer id, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging);
}
