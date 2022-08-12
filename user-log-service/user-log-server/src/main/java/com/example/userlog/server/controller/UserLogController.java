package com.example.userlog.server.controller;

import com.comit.services.userlog.client.dto.UserLogDto;
import com.comit.services.userlog.client.request.UserLogRequest;
import com.comit.services.userlog.client.response.BaseResponse;
import com.comit.services.userlog.client.response.UserLogListResponse;
import com.example.userlog.server.business.UserLogBusiness;
import com.example.userlog.server.constant.Const;
import com.example.userlog.server.constant.UserLogErrorCode;
import com.example.userlog.server.model.UserLog;
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
        return new ResponseEntity<>(new UserLogListResponse(UserLogErrorCode.SUCCESS.getCode(), UserLogErrorCode.SUCCESS.getMessage(), userLogDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> saveUserLog(@RequestBody UserLogRequest userLogRequest) {
        boolean saveUserLog = userLogBusiness.saveUserLog(userLogRequest);
        return new ResponseEntity<>(new BaseResponse(UserLogErrorCode.SUCCESS.getCode(), UserLogErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
    }
}
