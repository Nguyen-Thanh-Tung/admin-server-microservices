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
    Page<NotificationHistory> findByLocationIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndTypeInAndTimeBetweenOrderByTimeDesc(Integer locationId, List<String> types, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdInAndTimeBetweenOrderByTimeDesc(Integer location, List<Integer> employeeIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndTimeBetweenOrderByTimeDesc(Integer location, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndTimeBetweenOrderByTimeDesc(Integer location, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndTypeInAndEmployeeIdInAndTimeBetweenOrderByTimeDesc(Integer location, List<String> types, List<Integer> employeeIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdInAndTimeBetweenOrderByTimeDesc(Integer location, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndTypeInAndAreaRestrictionIdInAndTimeBetweenOrderByTimeDesc(Integer location, List<String> types, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndStatusOrderByTimeDesc(Integer location, String status, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdInAndTimeBetweenAndStatusOrderByTimeDesc(Integer location, List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, String status, Pageable paging);

    int countByLocationIdAndTimeBetween(Integer location, Date timeStart, Date timeEnd);

    int countByLocationIdAndTimeBetweenAndStatus(Integer location, Date timeStart, Date timeEnd, String status);

    @Query("select count (distinct o.areaRestrictionId) from NotificationHistory o where o.locationId = ?1 and o.time between ?2 and ?3")
    int countNumberAreaRestrictionHasNotify(Integer location, Date timeStart, Date timeEnd);

    @Query("select distinct o.areaRestrictionId from NotificationHistory o where o.locationId = ?1 and o.time between ?2 and ?3 and o.status = ?4")
    List<Integer> getAreaRestrictionHasNotifyNotResolve(Integer location, Date timeStart, Date timeEnd, String status);

    int countByAreaRestrictionIdAndTimeBetween(Integer areaRestrictionId, Date startDay, Date now);

    @Query("select count (distinct o.employeeId) from NotificationHistory o where o.locationId = ?1 and o.time between ?2 and ?3")
    int countNumberUserTimeKeepingNotificationInDay(Integer location, Date timeStart, Date timeEnd);

    NotificationHistory findById(int id);

    int countByLocationIdAndTimeBetweenAndType(Integer locationId, Date timeStart, Date timeEnd, String monthNotificationType);

    int countByLocationIdAndEmployeeIdAndTimeBetween(Integer locationId, Integer employeeId, Date time, Date time2);

    int countByAreaRestrictionIdAndTimeBetweenAndStatus(Integer areaRestrictionId, Date startDay, Date now, String status);
    
    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndCameraIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndCameraIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndCameraIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndCameraIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndAreaRestrictionIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndAreaRestrictionIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndCameraIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndCameraIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndCameraIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndCameraIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdNotNullAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndEmployeeIdIsNullAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdAndCameraIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdAndCameraIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer areaRestrictionId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndCameraIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer cameraId, String status, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndCameraIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Integer cameraId, Date timeStart, Date timeEnd, Pageable paging);

    Page<NotificationHistory> findByLocationIdAndStatusAndTimeBetweenOrderByTimeDesc(Integer locationId, String status, Date timeStart, Date timeEnd, Pageable paging);
}
