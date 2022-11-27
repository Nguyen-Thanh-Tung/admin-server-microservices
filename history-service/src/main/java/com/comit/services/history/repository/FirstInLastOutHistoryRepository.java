package com.comit.services.history.repository;

import com.comit.services.history.model.entity.FirstInLastOutHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FirstInLastOutHistoryRepository extends JpaRepository<FirstInLastOutHistory, Integer> {

    FirstInLastOutHistory findByEmployeeIdAndTypeAndTimeBetween(Integer employeeId, String checkIn, Date timeStart, Date timeEnd);

    Page<FirstInLastOutHistory> findByLocationIdAndTimeBetweenOrderByTimeDesc(Integer locationId, Date timeStart, Date timeEnd, Pageable paging);

    Page<FirstInLastOutHistory> findByCameraIdInAndTimeBetweenOrderByTimeDesc(List<Integer> cameraIds, Date timeStart, Date timeEnd, Pageable paging);

    Page<FirstInLastOutHistory> findByEmployeeIdAndTimeBetweenOrderByTimeDesc(Integer employeeId, Date timeStart, Date timeEnd, Pageable paging);

    Page<FirstInLastOutHistory> findByCameraIdInAndEmployeeIdAndTimeBetweenOrderByTimeDesc(List<Integer> cameraIds, Integer employeeId, Date timeStart, Date timeEnd, Pageable paging);
}
