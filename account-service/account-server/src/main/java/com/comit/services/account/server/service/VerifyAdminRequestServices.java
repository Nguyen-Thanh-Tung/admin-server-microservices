package com.comit.services.account.server.service;

import com.comit.services.account.client.request.ChangePasswordRequest;
import com.comit.services.account.client.request.ForgetPasswordRequest;
import com.comit.services.account.client.request.LoginRequest;
import com.comit.services.account.client.request.SignUpRequest;

public interface VerifyAdminRequestServices {
    void verifyLoginRequest(LoginRequest request);

    void verifyRegisterRequest(SignUpRequest request);

    void verifyForgetPasswordRequest(ForgetPasswordRequest request);

    void verifyChangePasswordRequest(ChangePasswordRequest request);
}
