package com.comit.services.history.repository;

import com.comit.services.history.model.entity.InOutHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InOutHistoryRepository extends JpaRepository<InOutHistory, Integer> {
    Page<InOutHistory> findByCameraIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(List<Integer> cameraIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<InOutHistory> findByEmployeeIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer employeeId, Date timeStart, Date timeEnd, Pageable paging);

    Page<InOutHistory> findByCameraIdInAndEmployeeIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(List<Integer> cameraIds, Integer employeeId, Date timeStart, Date timeEnd, Pageable paging);

    Page<InOutHistory> findByLocationIdAndTimeAfterAndTimeBeforeOrderByTimeDesc(Integer locationId, Date timeStart, Date timeEnd, Pageable paging);

    Page<InOutHistory> findByAreaRestrictionIdInAndTimeAfterAndTimeBeforeOrderByTimeDesc(List<Integer> areaRestrictionIds, Date timeStart, Date timeEnd, Pageable paging);

    @Query("select count (distinct o.employeeId) from InOutHistory o where o.locationId = ?1 and o.time between ?2 and ?3 and o.type = ?4")
    int countNumberCheckIn(Integer locationId, Date timeStart, Date timeEnd, String type);

    int countByLocationIdAndEmployeeIdAndTimeAfterAndTimeBefore(Integer locationId, Integer employeeId, Date timeStart, Date timeEnd);
}
