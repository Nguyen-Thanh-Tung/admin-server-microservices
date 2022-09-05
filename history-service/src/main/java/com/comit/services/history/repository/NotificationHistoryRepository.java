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

    Page<NotificationHistory> findByLocationIdAndAreaRestrictionIdInAndStatusOrderByTimeDesc(Integer location, List<Integer> areaRestrictionIds, String status, Pageable paging);

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
}
