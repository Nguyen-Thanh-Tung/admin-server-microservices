package com.comit.services.userLog.business;

import com.comit.services.userLog.controller.request.UserLogRequest;
import com.comit.services.userLog.model.dto.UserLogDto;
import com.comit.services.userLog.util.TimeUtil;
import com.comit.services.userLog.model.entity.User;
import com.comit.services.userLog.model.entity.UserLog;
import com.comit.services.userLog.service.UserLogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserLogBusinessImpl implements UserLogBusiness {
    @Autowired
    private UserLogServices userLogServices;
    @Autowired
    private TimeUtil timeUtil;

    @Override
    public Page<UserLog> getUserLogPage(Integer userId, String time, int page, int size, String search) throws ParseException {
        Pageable paging = PageRequest.of(page, size);

        Page<UserLog> pageUserLogs;
        if (userId == null && time == null) {
            User currentUser = userLogServices.getCurrentUser();
            List<Integer> userOfCurrentUserIds = new ArrayList<>();
            userOfCurrentUserIds.add(currentUser.getId());
            List<User> users = userLogServices.getAllUserOfCurrentUser();
            users.forEach(user -> {
                userOfCurrentUserIds.add(user.getId());
            });
            pageUserLogs = userLogServices.findByUsers(userOfCurrentUserIds, search, paging);
        } else if (time == null) {
            pageUserLogs = userLogServices.findByUsers(List.of(userId), search, paging);
        } else if (userId == null) {
            Date dateTime = timeUtil.parsingDateStringToDate(time);
            pageUserLogs = userLogServices.findByTime(dateTime, search, paging);
        } else {
            Date dateTime = timeUtil.parsingDateStringToDate(time);
            pageUserLogs = userLogServices.findByUserAndTime(userId, dateTime, search, paging);
        }

        return pageUserLogs;
    }

    @Override
    public List<UserLogDto> getAllUserLog(List<UserLog> userLogs) {
        List<UserLogDto> userLogDtos = new ArrayList<>();
        userLogs.forEach(userLog -> {
            userLogDtos.add(UserLogDto.convertUserLogToUserLogDto(userLog));
        });
        return userLogDtos;
    }

    @Override
    public boolean saveUserLog(UserLogRequest userLogRequest) {
        UserLog userLog = new UserLog();
        userLog.setContent(userLogRequest.getContent());
        userLog.setTime(userLogRequest.getTime());
        userLog.setUserId(userLogRequest.getUserId());
        return userLogServices.saveUserLog(userLog);
    }
}
