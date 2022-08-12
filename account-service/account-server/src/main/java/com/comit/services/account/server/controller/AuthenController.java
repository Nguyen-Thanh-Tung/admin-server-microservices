package com.comit.services.account.server.controller;

import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.client.request.ChangePasswordRequest;
import com.comit.services.account.client.request.ForgetPasswordRequest;
import com.comit.services.account.client.request.LoginRequest;
import com.comit.services.account.client.response.BaseResponse;
import com.comit.services.account.client.response.LoginResponse;
import com.comit.services.account.server.business.AuthBusiness;
import com.comit.services.account.server.constant.UserErrorCode;
import com.comit.services.account.server.jwt.JwtProvider;
import com.comit.services.account.server.service.VerifyAdminRequestServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth")
public class AuthenController {
    @Autowired
    AuthBusiness authBusiness;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    VerifyAdminRequestServicesImpl verifyRequestServices;

    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest request) {
        String token = authBusiness.getTokenLogin(request);
        httpServletRequest.setAttribute("token", token);
        UserDto userDto = authBusiness.login(request);
        return new ResponseEntity<>(new LoginResponse(UserErrorCode.SUCCESS.getCode(), UserErrorCode.SUCCESS.getMessage(), userDto, token), HttpStatus.OK);
    }

    @PostMapping(value = "/forget-password")
    public ResponseEntity<BaseResponse> forgetPassword(@RequestBody ForgetPasswordRequest request) {
        authBusiness.forgetPassword(request);
        return new ResponseEntity<>(new BaseResponse(UserErrorCode.SUCCESS.getCode(), UserErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<BaseResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        authBusiness.changePassword(request);
        return new ResponseEntity<>(new BaseResponse(UserErrorCode.SUCCESS.getCode(), UserErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
    }
}
