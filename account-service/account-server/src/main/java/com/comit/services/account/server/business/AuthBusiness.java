package com.comit.services.account.server.business;

import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.client.request.ChangePasswordRequest;
import com.comit.services.account.client.request.ForgetPasswordRequest;
import com.comit.services.account.client.request.LoginRequest;
import com.comit.services.account.client.request.SignUpRequest;
import com.comit.services.account.server.model.User;

public interface AuthBusiness {
    String getTokenLogin(LoginRequest request);

    UserDto login(LoginRequest request);

    UserDto register(SignUpRequest request);

    UserDto init(String organizationName, String username, String email, String password);

    User changePassword(ChangePasswordRequest request);

    void forgetPassword(ForgetPasswordRequest request);
}
