package com.comit.services.history.server.business;

import com.comit.services.history.client.dto.NotificationHistoryDto;
import com.comit.services.history.client.request.NotificationHistoryRequest;
import com.comit.services.history.server.model.entity.NotificationHistory;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface NotificationHistoryBusiness {
    Page<NotificationHistory> getNotificationHistoryPage(String types, String employeeIds, String timeStart, String timeEnd, Integer locationId, int page, int size) throws ParseException;

    Page<NotificationHistory> getNotificationHistoryPage(boolean hasEmployee, String timeStart, String timeEnd, Integer locationId, int page, int size) throws ParseException;

    Page<NotificationHistory> getNotificationHistoryPage(String status, int page, int size) throws ParseException;

    Page<NotificationHistory> getNotificationHistoryPage(String types, String timeStart, String timeEnd, Integer locationId, String areaRestrictionIds, int page, int size) throws ParseException;

    List<NotificationHistoryDto> getAllNotificationHistory(List<NotificationHistory> content);

    NotificationHistoryDto saveNotificationHistory(NotificationHistoryRequest request) throws ParseException;

    Page<NotificationHistory> getNotificationHistoryPage(String areaRestrictionIds, String status, int page, int size);

    boolean updateStatusNotificationHistory(Integer id);

    int getNumberUserNotificationInCurrenDay();

    int getNumberLateInMonth();

    int getNumberNotificationInDay();

    int getNumberNotificationNotResolve();

    int getNumberAreaRestrictionHasNotify();

    int getNumberARHasNotifyNotResolveAndUsingRing();

    int getNumberNotificationOfAreaRestriction(Integer areaRestrictionId);
}
