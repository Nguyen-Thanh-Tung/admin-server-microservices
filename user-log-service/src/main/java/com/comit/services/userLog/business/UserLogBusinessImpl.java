package com.comit.services.userLog.business;

import com.comit.services.userLog.client.data.UserDtoClient;
import com.comit.services.userLog.controller.request.UserLogRequest;
import com.comit.services.userLog.model.dto.UserLogDto;
import com.comit.services.userLog.model.entity.UserLog;
import com.comit.services.userLog.service.UserLogServices;
import com.comit.services.userLog.util.TimeUtil;
import org.modelmapper.ModelMapper;
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
            UserDtoClient currentUserDtoClient = userLogServices.getCurrentUser();
            List<Integer> userOfCurrentUserIds = new ArrayList<>();
            userOfCurrentUserIds.add(currentUserDtoClient.getId());
            List<UserDtoClient> userDtoClients = userLogServices.getAllUserOfCurrentUser();
            userDtoClients.forEach(user -> {
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
            userLogDtos.add(convertUserLogToUserLogDto(userLog));
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

    public UserLogDto convertUserLogToUserLogDto(UserLog userLog) {
        ModelMapper modelMapper = new ModelMapper();
        UserLogDto userLogDto = modelMapper.map(userLog, UserLogDto.class);
        UserDtoClient userDtoClient = userLogServices.getUserById(userLog.getUserId());
        userLogDto.setUsername(userDtoClient.getUsername());
        return userLogDto;
    }
}
