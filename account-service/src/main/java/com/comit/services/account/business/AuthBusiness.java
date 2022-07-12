package com.comit.services.account.business;

import com.comit.services.account.controller.request.ChangePasswordRequest;
import com.comit.services.account.controller.request.ForgetPasswordRequest;
import com.comit.services.account.controller.request.LoginRequest;
import com.comit.services.account.controller.request.SignUpRequest;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.model.entity.User;

import java.io.IOException;

public interface AuthBusiness {
    String getTokenLogin(LoginRequest request);

    UserDto login(LoginRequest request) throws IOException;

    UserDto register(SignUpRequest request) throws IOException;

    UserDto init(String organizationName, String username, String email, String password) throws IOException;

    User changePassword(ChangePasswordRequest request);

    void forgetPassword(ForgetPasswordRequest request);
}
