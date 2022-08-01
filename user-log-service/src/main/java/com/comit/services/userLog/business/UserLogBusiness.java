package com.comit.services.userLog.business;

import com.comit.services.userLog.controller.request.UserLogRequest;
import com.comit.services.userLog.model.dto.UserLogDto;
import com.comit.services.userLog.model.entity.UserLog;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface UserLogBusiness {
    Page<UserLog> getUserLogPage(Integer userId, String time, int page, int size, String search) throws ParseException;

    List<UserLogDto> getAllUserLog(List<UserLog> userLogs);

    boolean saveUserLog(UserLogRequest userLogRequest);
}
