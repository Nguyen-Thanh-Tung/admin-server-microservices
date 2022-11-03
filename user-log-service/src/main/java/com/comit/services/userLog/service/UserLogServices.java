package com.comit.services.userLog.service;

import com.comit.services.userLog.client.data.UserDtoClient;
import com.comit.services.userLog.model.entity.UserLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface UserLogServices {
    Page<UserLog> getAllUserLogs(String search, Pageable paging);

    Page<UserLog> findByUsers(List<Integer> userIds, String search, Pageable paging);

    Page<UserLog> findByTime(Date time, String search, Pageable paging);

    Page<UserLog> findByUserAndTime(Integer userId, Date time, String search, Pageable paging);

    boolean saveUserLog(UserLog userLog);

    UserDtoClient getCurrentUser();

    List<UserDtoClient> getAllUserOfCurrentUser();

    UserDtoClient getUserById(Integer userId);
}
