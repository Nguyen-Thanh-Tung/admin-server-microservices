package com.comit.services.account.service;

import com.comit.services.account.controller.request.ChangePasswordRequest;
import com.comit.services.account.controller.request.ForgetPasswordRequest;
import com.comit.services.account.controller.request.LoginRequest;
import com.comit.services.account.controller.request.SignUpRequest;

public interface VerifyAdminRequestServices {
    void verifyLoginRequest(LoginRequest request);

    void verifyRegisterRequest(SignUpRequest request);

    void verifyForgetPasswordRequest(ForgetPasswordRequest request);

    void verifyChangePasswordRequest(ChangePasswordRequest request);
}
