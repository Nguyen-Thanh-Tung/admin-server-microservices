package com.comit.services.userLog.repository;

import com.comit.services.userLog.model.entity.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Integer> {

    Page<UserLog> findByUserIdInOrderByTimeDesc(List<Integer> userIds, Pageable pageable);

    Page<UserLog> findByTimeGreaterThanEqualAndTimeLessThanOrderByTimeDesc(Date timeStart, Date timeEnd, Pageable pageable);

    Page<UserLog> findByUserIdAndTimeGreaterThanEqualAndTimeLessThanOrderByTimeDesc(Integer userId, Date timeStart, Date timeEnd, Pageable pageable);

    Page<UserLog> findByContentContainingOrderByTimeDesc(String search, Pageable paging);

    Page<UserLog> findByUserIdInAndContentContainingOrderByTimeDesc(List<Integer> userIds, String search, Pageable paging);

    Page<UserLog> findByTimeGreaterThanEqualAndTimeLessThanAndContentContainingOrderByTimeDesc(Date time, Date date, String search, Pageable paging);

    Page<UserLog> findByUserIdAndTimeGreaterThanEqualAndTimeLessThanAndContentContainingOrderByTimeDesc(Integer userId, Date time, Date date, String search, Pageable paging);
}
