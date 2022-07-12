package com.comit.services.account.controller;
import com.comit.services.account.business.AuthBusiness;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.request.ChangePasswordRequest;
import com.comit.services.account.controller.request.ForgetPasswordRequest;
import com.comit.services.account.controller.request.LoginRequest;
import com.comit.services.account.controller.response.BaseResponse;
import com.comit.services.account.controller.response.LoginResponse;
import com.comit.services.account.jwt.JwtProvider;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.service.VerifyAdminRequestServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/auth")
public class AuthenController {
    @Autowired
    AuthBusiness authBusiness;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    VerifyAdminRequestServicesImpl verifyRequestServices;

    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginRequest request) throws IOException {
        UserDto userDto = authBusiness.login(request);
        String token = authBusiness.getTokenLogin(request);
        return new ResponseEntity<>(new LoginResponse(UserErrorCode.SUCCESS, userDto, token), HttpStatus.OK);
    }

    @PostMapping(value = "/forget-password")
    public ResponseEntity<BaseResponse> forgetPassword(@RequestBody ForgetPasswordRequest request) {
        authBusiness.forgetPassword(request);
        return new ResponseEntity<>(new BaseResponse(UserErrorCode.SUCCESS), HttpStatus.OK);
    }

    @PostMapping(value = "/change-password")
    public ResponseEntity<BaseResponse> changePassword(@RequestBody ChangePasswordRequest request) {
        authBusiness.changePassword(request);
        return new ResponseEntity<>(new BaseResponse(UserErrorCode.SUCCESS), HttpStatus.OK);
    }
}
