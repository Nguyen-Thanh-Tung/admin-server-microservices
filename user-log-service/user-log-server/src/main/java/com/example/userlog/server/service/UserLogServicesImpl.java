package com.example.userlog.server.service;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.client.response.UserListResponse;
import com.comit.services.account.client.response.UserResponse;
import com.example.userlog.server.constant.Const;
import com.example.userlog.server.constant.UserLogErrorCode;
import com.example.userlog.server.exception.RestApiException;
import com.example.userlog.server.model.UserLog;
import com.example.userlog.server.repository.UserLogRepository;
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
    public UserDto getCurrentUser() {
        UserResponse userResponse = accountClient.getCurrentUser(httpServletRequest.getHeader("token"));
        if (userResponse == null) {
            throw new RestApiException(UserLogErrorCode.INTERNAL_ERROR);
        }
        return userResponse.getUserDto();
    }


    @Override
    public List<UserDto> getAllUserOfCurrentUser() {
        UserListResponse userListResponse = accountClient.getAllUsersOfCurrentUser(httpServletRequest.getHeader("token"));
        if (userListResponse == null) {
            throw new RestApiException(UserLogErrorCode.INTERNAL_ERROR);
        }
        return userListResponse.getUserDtos();
    }

    @Override
    public UserDto getUserById(Integer userId) {
        UserResponse userResponse = accountClient.getUserById(httpServletRequest.getHeader("token"), userId);
        if (userResponse == null) {
            throw new RestApiException(UserLogErrorCode.INTERNAL_ERROR);
        }
        return userResponse.getUserDto();
    }
}
