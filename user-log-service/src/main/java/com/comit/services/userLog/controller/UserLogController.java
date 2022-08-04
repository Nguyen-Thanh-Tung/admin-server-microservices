package com.comit.services.userLog.controller;

import com.comit.services.userLog.business.UserLogBusiness;
import com.comit.services.userLog.constant.Const;
import com.comit.services.userLog.constant.UserLogErrorCode;
import com.comit.services.userLog.controller.request.UserLogRequest;
import com.comit.services.userLog.controller.response.BaseResponse;
import com.comit.services.userLog.controller.response.UserLogListResponse;
import com.comit.services.userLog.model.dto.UserLogDto;
import com.comit.services.userLog.model.entity.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/user-logs")
public class UserLogController {
    @Autowired
    private UserLogBusiness userLogBusiness;

    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllUserLogs(
            @RequestParam(required = false, value = "user_id") Integer userId,
            @RequestParam(required = false) String time,
            @RequestParam(defaultValue = Const.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Const.DEFAULT_SIZE_PAGE) int size,
            @RequestParam(required = false) String search
    ) throws ParseException {
        Page<UserLog> userLogPage = userLogBusiness.getUserLogPage(userId, time, page, size, search);

        List<UserLogDto> userLogDtos = new ArrayList<>();
        int currentPage = 0;
        long totalItems = 0;
        int totalPages = 0;
        if (userLogPage != null) {
            userLogDtos = userLogBusiness.getAllUserLog(userLogPage.getContent());
            currentPage = userLogPage.getNumber();
            totalItems = userLogPage.getTotalElements();
            totalPages = userLogPage.getTotalPages();
        }
        return new ResponseEntity<>(new UserLogListResponse(UserLogErrorCode.SUCCESS, userLogDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> saveUserLog(@RequestBody UserLogRequest userLogRequest) {
        boolean saveUserLog = userLogBusiness.saveUserLog(userLogRequest);
        return new ResponseEntity<>(new BaseResponse(UserLogErrorCode.SUCCESS), HttpStatus.OK);
    }
}
