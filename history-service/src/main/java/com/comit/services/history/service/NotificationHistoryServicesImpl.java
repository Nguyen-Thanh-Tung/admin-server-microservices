package com.comit.services.history.service;

import com.comit.services.history.client.AreaRestrictionClient;
import com.comit.services.history.client.response.NotificationMethodResponseClient;
import com.comit.services.history.constant.Const;
import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.exception.RestApiException;
import com.comit.services.history.model.entity.NotificationHistory;
import com.comit.services.history.repository.NotificationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NotificationHistoryServicesImpl implements NotificationHistoryServices {

    @Autowired
    private NotificationHistoryRepository notificationHistoryRepository;
    @Autowired
    private AreaRestrictionClient areaRestrictionClient;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, Date timeStart, Date timeEnd, Pageable paging) {
        return notificationHistoryRepository.findByLocationIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageType(Integer locationId, List<String> types, Date timeStart, Date timeEnd, Pageable paging) {
        return notificationHistoryRepository.findByLocationIdAndTypeInAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, types, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, List<Integer> employeeIds, Date timeStart, Date timeEnd, Pageable paging) {
        return notificationHistoryRepository.findByLocationIdAndEmployeeIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, employeeIds, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, boolean hasEmployee, Date timeStart, Date timeEnd, Pageable paging) {
        if (hasEmployee) {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdNotNullAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, timeStart, timeEnd, paging);
        } else {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdIsNullAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, timeStart, timeEnd, paging);
        }
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageType(Integer locationId, List<String> types, List<Integer> employeeIds, Date timeStart, Date timeEnd, Pageable paging) {
        return notificationHistoryRepository.findByLocationIdAndTypeInAndEmployeeIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, types, employeeIds, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageAreaRestriction(Integer locationId, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging) {
        return notificationHistoryRepository.findByLocationIdAndAreaRestrictionIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionIds, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageTypeAreaRestriction(Integer locationId, List<String> types, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging) {
        return notificationHistoryRepository.findByLocationIdAndTypeInAndAreaRestrictionIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, types, areaRestrictionIds, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, String status, Pageable paging) {
        return notificationHistoryRepository.findByLocationIdAndStatusOrderByTimeDesc(locationId, status, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, String status, Pageable paging) {
        return notificationHistoryRepository.findByLocationIdAndAreaRestrictionIdInAndTimeAfterAndTimeBeforeAndStatusOrderByTimeDesc(locationId, areaRestrictionIds, timeStart, timeEnd, status, paging);
    }

    @Override
    public NotificationHistory saveNotificationHistory(NotificationHistory notificationHistory) {
        return notificationHistoryRepository.save(notificationHistory);
    }

    @Override
    public int getNumberNotificationInDay(Integer locationId, Date timeStart, Date timeEnd) {
        return notificationHistoryRepository.countByLocationIdAndTimeAfterAndTimeBefore(locationId, timeStart, timeEnd);
    }

    @Override
    public int getNumberNotificationNotResolve(Integer locationId, Date timeStart, Date timeEnd) {
        return notificationHistoryRepository.countByLocationIdAndTimeAfterAndTimeBeforeAndStatus(locationId, timeStart, timeEnd, "Chưa xử lý");
    }

    @Override
    public int getNumberAreaRestrictionHasNotify(Integer locationId, Date timeStart, Date timeEnd) {
        return notificationHistoryRepository.countNumberAreaRestrictionHasNotify(locationId, timeStart, timeEnd);
    }

    @Override
    public int getNumberARHasNotifyNotResolveAndUsingRing(Integer locationId, Date timeStart, Date timeEnd) {
        List<Integer> areaRestrictionIds = notificationHistoryRepository.getAreaRestrictionHasNotifyNotResolve(locationId, timeStart, timeEnd, "Chưa xử lý");
        AtomicInteger numberARHasNotifyNotResolveAndUsingRing = new AtomicInteger(0);
        areaRestrictionIds.forEach(areaRestrictionId -> {
            NotificationMethodResponseClient notificationMethodResponseClient = areaRestrictionClient.getNotificationMethodOfAreaRestriction(httpServletRequest.getHeader("token"), areaRestrictionId).getBody();
            if (notificationMethodResponseClient != null && notificationMethodResponseClient.getNotificationMethod().isUseRing()) {
                numberARHasNotifyNotResolveAndUsingRing.getAndIncrement();
            }
        });
        return numberARHasNotifyNotResolveAndUsingRing.get();
    }

    @Override
    public int getNumberNotificationOfAreaRestriction(Integer areaRestrictionId, Date startDay, Date now, String status) {
        if (status == null) {
            return notificationHistoryRepository.countByAreaRestrictionIdAndTimeAfterAndTimeBefore(areaRestrictionId, startDay, now);
        }
        return notificationHistoryRepository.countByAreaRestrictionIdAndTimeAfterAndTimeBeforeAndStatus(areaRestrictionId, startDay, now, status);
    }

    @Override
    public boolean updateStatusNotificationHistory(int id) {
        NotificationHistory notificationHistory = notificationHistoryRepository.findById(id);
        if (notificationHistory == null) {
            throw new RestApiException(HistoryErrorCode.NOT_EXIST_NOTIFICATION_HISTORY);
        }
        notificationHistory.setStatus("Đã xử lý");
        NotificationHistory newNotificationHistory = notificationHistoryRepository.save(notificationHistory);
        return Objects.equals(newNotificationHistory.getStatus(), "Đã xử lý");
    }

    @Override
    public int getNumberUserNotificationInDay(Integer locationId, Date timeStart, Date timeEnd) {
        return notificationHistoryRepository.countNumberUserTimeKeepingNotificationInDay(locationId, timeStart, timeEnd);
    }

    @Override
    public int getNumberLateInMonth(Integer locationId, Date timeStart, Date timeEnd) {
        return notificationHistoryRepository.countByLocationIdAndTimeAfterAndTimeBeforeAndType(locationId, timeStart, timeEnd, Const.MONTH_NOTIFICATION_TYPE);
    }

    @Override
    public boolean hasHistory(Integer locationId, Integer employeeId, Date timeStart, Date timeEnd) {
        return notificationHistoryRepository.countByLocationIdAndEmployeeIdAndTimeAfterAndTimeBefore(locationId, employeeId, timeStart, timeEnd) > 0;
    }

    @Override
    public int getNumberNotification(Integer locationId, Integer employeeId, Date timeStart, Date timeEnd) {
        return notificationHistoryRepository.countByLocationIdAndEmployeeIdAndTimeAfterAndTimeBefore(locationId, employeeId, timeStart, timeEnd);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndCameraIdAndStatus(Integer locationId, Integer areaRestrictionId, Integer cameraId, String status, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging) {
        if (hasEmployee == null) {
            return notificationHistoryRepository.findByLocationIdAndAreaRestrictionIdAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, cameraId, status, timeStart, timeEnd, paging);
        }
        if (hasEmployee) {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, cameraId, status, timeStart, timeEnd, paging);
        }
        return notificationHistoryRepository.findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, cameraId, status, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndCameraId(Integer locationId, Integer areaRestrictionId, Integer cameraId, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging) {
        if (hasEmployee == null) {
            return notificationHistoryRepository.findByLocationIdAndAreaRestrictionIdAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, cameraId, timeStart, timeEnd, paging);
        }
        if (hasEmployee) {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, cameraId, timeStart, timeEnd, paging);
        }
        return notificationHistoryRepository.findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, cameraId, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionIdAndStatus(Integer locationId, Integer areaRestrictionId, String status, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging) {
        if (hasEmployee == null) {
            return notificationHistoryRepository.findByLocationIdAndAreaRestrictionIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, status, timeStart, timeEnd, paging);
        }
        if (hasEmployee) {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, status, timeStart, timeEnd, paging);
        }
        return notificationHistoryRepository.findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, status, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByAreaRestrictionId(Integer locationId, Integer areaRestrictionId, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging) {
        if (hasEmployee == null) {
            return notificationHistoryRepository.findByLocationIdAndAreaRestrictionIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, timeStart, timeEnd, paging);
        }
        if (hasEmployee) {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, timeStart, timeEnd, paging);
        }
        return notificationHistoryRepository.findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, areaRestrictionId, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByCameraIdAndStatus(Integer locationId, Integer cameraId, String status, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging) {
        if (hasEmployee == null) {
            return notificationHistoryRepository.findByLocationIdAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, cameraId, status, timeStart, timeEnd, paging);
        }
        if (hasEmployee) {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdNotNullAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, cameraId, status, timeStart, timeEnd, paging);
        }
        return notificationHistoryRepository.findByLocationIdAndEmployeeIdIsNullAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, cameraId, status, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByCameraId(Integer locationId, Integer cameraId, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging) {
        if (hasEmployee == null) {
            return notificationHistoryRepository.findByLocationIdAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, cameraId, timeStart, timeEnd, paging);
        }
        if (hasEmployee) {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdNotNullAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, cameraId, timeStart, timeEnd, paging);
        }
        return notificationHistoryRepository.findByLocationIdAndEmployeeIdIsNullAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, cameraId, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPageByStatus(Integer locationId, String status, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging) {
        if (hasEmployee == null) {
            return notificationHistoryRepository.findByLocationIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, status, timeStart, timeEnd, paging);
        }
        if (hasEmployee) {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdNotNullAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, status, timeStart, timeEnd, paging);
        }
        return notificationHistoryRepository.findByLocationIdAndEmployeeIdIsNullAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, status, timeStart, timeEnd, paging);
    }

    @Override
    public Page<NotificationHistory> getNotificationHistoryPage(Integer locationId, Date timeStart, Date timeEnd, Boolean hasEmployee, Pageable paging) {
        if (hasEmployee == null) {
            return notificationHistoryRepository.findByLocationIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, timeStart, timeEnd, paging);
        }
        if (hasEmployee) {
            return notificationHistoryRepository.findByLocationIdAndEmployeeIdNotNullAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, timeStart, timeEnd, paging);
        }
        return notificationHistoryRepository.findByLocationIdAndEmployeeIdIsNullAndTimeAfterAndTimeBeforeOrderByTimeDesc(locationId, timeStart, timeEnd, paging);
    }
}
