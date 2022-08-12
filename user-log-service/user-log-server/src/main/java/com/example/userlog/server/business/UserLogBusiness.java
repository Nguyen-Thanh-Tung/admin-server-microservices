package com.example.userlog.server.business;

import com.comit.services.userlog.client.dto.UserLogDto;
import com.comit.services.userlog.client.request.UserLogRequest;
import com.example.userlog.server.model.UserLog;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface UserLogBusiness {
    Page<UserLog> getUserLogPage(Integer userId, String time, int page, int size, String search) throws ParseException;

    List<UserLogDto> getAllUserLog(List<UserLog> userLogs);

    boolean saveUserLog(UserLogRequest userLogRequest);
}
