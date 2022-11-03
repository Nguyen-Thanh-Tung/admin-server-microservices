package com.comit.services.history.repository;

import com.comit.services.history.model.entity.NotificationHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Integer> {
    Page<NotificationHistory> findByLocationIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndTypeInAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, List<String> types, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer location, List<Integer> employeeIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer location, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer location, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndTypeInAndEmployeeIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer location, List<String> types, List<Integer> employeeIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer location, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndTypeInAndAreaRestrictionIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer location, List<String> types, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndStatusOrderByTimeDesc(Integer location, String status, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdInAndTimeAfterAndTimeBeforeAndStatusOrderByTimeDesc(Integer location, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, String status, Pageable paging);

    int countByLocationIdAndTimeAfterAndTimeBefore(Integer location, Date timeStart, Date timeEnd);

    int countByLocationIdAndTimeAfterAndTimeBeforeAndStatus(Integer location, Date timeStart, Date timeEnd, String status);

    @Query("select count (distinct o.areaRestrictionId) from NotificationHistory o where o.locationId = ?1 and o.time between ?2 and ?3")
    int countNumberAreaRestrictionHasNotify(Integer location, Date timeStart, Date timeEnd);

    @Query("select distinct o.areaRestrictionId from NotificationHistory o where o.locationId = ?1 and o.time between ?2 and ?3 and o.status = ?4")
    List<Integer> getAreaRestrictionHasNotifyNotResolve(Integer location, Date timeStart, Date timeEnd, String status);

    int countByAreaRestrictionIdAndTimeAfterAndTimeBefore(Integer areaRestrictionId, Date startDay, Date now);

    @Query("select count (distinct o.employeeId) from NotificationHistory o where o.locationId = ?1 and o.time between ?2 and ?3")
    int countNumberUserTimeKeepingNotificationInDay(Integer location, Date timeStart, Date timeEnd);

    NotificationHistory findById(int id);

    int countByLocationIdAndTimeAfterAndTimeBeforeAndType(Integer locationId, Date timeStart, Date timeEnd, String monthNotificationType);

    int countByLocationIdAndEmployeeIdAndTimeAfterAndTimeBefore(Integer locationId, Integer employeeId, Date time, Date time2);

    int countByAreaRestrictionIdAndTimeAfterAndTimeBeforeAndStatus(Integer areaRestrictionId, Date startDay, Date now, String status);
    
    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndCameraIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndCameraIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndStatusAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, String status, Date timeStart, Date timeEnd, Pageable paging);
}
