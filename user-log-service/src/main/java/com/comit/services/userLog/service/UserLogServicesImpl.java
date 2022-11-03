package com.comit.services.userLog.service;

import com.comit.services.userLog.client.AccountClient;
import com.comit.services.userLog.client.data.UserDtoClient;
import com.comit.services.userLog.client.response.UserListResponseClient;
import com.comit.services.userLog.client.response.UserResponseClient;
import com.comit.services.userLog.constant.Const;
import com.comit.services.userLog.constant.UserLogErrorCode;
import com.comit.services.userLog.exception.RestApiException;
import com.comit.services.userLog.model.entity.UserLog;
import com.comit.services.userLog.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class UserLogServicesImpl implements UserLogServices {
    @Autowired
    private UserLogRepository userLogRepository;
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public Page<UserLog> getAllUserLogs(String search, Pageable paging) {
        if (search != null && !search.trim().isEmpty()) {
            return userLogRepository.findByContentContainingOrderByTimeDesc(search, paging);
        }
        return userLogRepository.findAll(paging);
    }

    @Override
    public Page<UserLog> findByUsers(List<Integer> userIds, String search, Pageable paging) {
        if (search != null && !search.trim().isEmpty()) {
            return userLogRepository.findByUserIdInAndContentContainingOrderByTimeDesc(userIds, search, paging);
        }
        return userLogRepository.findByUserIdInOrderByTimeDesc(userIds, paging);
    }

    @Override
    public Page<UserLog> findByTime(Date time, String search, Pageable paging) {
        if (search != null && !search.trim().isEmpty()) {
            return userLogRepository.findByTimeGreaterThanEqualAndTimeLessThanAndContentContainingOrderByTimeDesc(time, new Date(time.getTime() + Const.ONE_DAY), search, paging);
        }
        return userLogRepository.findByTimeGreaterThanEqualAndTimeLessThanOrderByTimeDesc(time, new Date(time.getTime() + Const.ONE_DAY), paging);
    }

    @Override
    public Page<UserLog> findByUserAndTime(Integer userId, Date time, String search, Pageable paging) {
        if (search != null && !search.trim().isEmpty()) {
            return userLogRepository.findByUserIdAndTimeGreaterThanEqualAndTimeLessThanAndContentContainingOrderByTimeDesc(userId, time, new Date(time.getTime() + Const.ONE_DAY), search, paging);
        }
        return userLogRepository.findByUserIdAndTimeGreaterThanEqualAndTimeLessThanOrderByTimeDesc(userId, time, new Date(time.getTime() + Const.ONE_DAY), paging);
    }

    @Override
    public boolean saveUserLog(UserLog userLog) {
        try {
            userLogRepository.save(userLog);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDtoClient getCurrentUser() {
        UserResponseClient userResponseClient = accountClient.getCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (userResponseClient == null) {
            throw new RestApiException(UserLogErrorCode.INTERNAL_ERROR);
        }
        return userResponseClient.getUser();
    }


    @Override
    public List<UserDtoClient> getAllUserOfCurrentUser() {
        UserListResponseClient userListResponseClient = accountClient.getAllUsersOfCurrentUser(httpServletRequest.getHeader("token")).getBody();
        if (userListResponseClient == null) {
            throw new RestApiException(UserLogErrorCode.INTERNAL_ERROR);
        }
        return userListResponseClient.getUsers();
    }

    @Override
    public UserDtoClient getUserById(Integer userId) {
        UserResponseClient userResponseClient = accountClient.getUserById(httpServletRequest.getHeader("token"), userId).getBody();
        if (userResponseClient == null) {
            throw new RestApiException(UserLogErrorCode.INTERNAL_ERROR);
        }
        return userResponseClient.getUser();
    }
}
