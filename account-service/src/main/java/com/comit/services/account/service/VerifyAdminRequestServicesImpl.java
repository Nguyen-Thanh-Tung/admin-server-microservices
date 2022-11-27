package com.comit.services.account.service;

import com.comit.services.account.constant.AuthErrorCode;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.request.ChangePasswordRequest;
import com.comit.services.account.controller.request.ForgetPasswordRequest;
import com.comit.services.account.controller.request.LoginRequest;
import com.comit.services.account.controller.request.SignUpRequest;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.exeption.AuthException;
import com.comit.services.account.util.ValidateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class VerifyAdminRequestServicesImpl implements VerifyAdminRequestServices {
    @Autowired
    private ValidateField validateField;

    @Override
    public void verifyLoginRequest(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        if (username == null || username.equals("")) {
            throw new AuthException(AuthErrorCode.MISSING_USERNAME_FIELD);
        }
        if (password == null || password.equals("")) {
            throw new AuthException(AuthErrorCode.MISSING_PASSWORD_FIELD);
        }

        if (!validateField.validUsername(username)) {
            throw new AuthException(AuthErrorCode.USERNAME_INVALID);
        }

        if (!validateField.validPassword(password)) {
            throw new AuthException(AuthErrorCode.PASSWORD_INVALID);
        }
    }

    @Override
    public void verifyRegisterRequest(SignUpRequest request) {
        String fullname = request.getFullname();
        String email = request.getEmail();
        String password = request.getPassword();
        Integer organizationId = request.getOrganizationId();
        Set<String> roles = request.getRoles();

        if (fullname == null || fullname.trim().equals("")) {
            throw new AccountRestApiException(UserErrorCode.MISSING_FULLNAME_FIELD);
        }

        if (email == null || email.trim().equals("")) {
            throw new AccountRestApiException(UserErrorCode.MISSING_EMAIL_FIELD);
        }

        if (password == null || password.trim().equals("")) {
            throw new AuthException(AuthErrorCode.MISSING_PASSWORD_FIELD);
        }
    }

    @Override
    public void verifyForgetPasswordRequest(ForgetPasswordRequest request) {
        String email = request.getEmail();

        if (email == null) {
            throw new AccountRestApiException(UserErrorCode.MISSING_EMAIL_FIELD);
        } else {
            if (!validateField.validEmail(email)) {
                throw new AccountRestApiException(UserErrorCode.EMAIL_INVALID);
            }
        }
    }

    @Override
    public void verifyChangePasswordRequest(ChangePasswordRequest request) {
        Integer userId = request.getUserId();
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        String code = request.getCode();

        if (userId == null) {
            throw new AuthException(AuthErrorCode.MISSING_USER_ID_FIELD);
        }

        if ((oldPassword == null || oldPassword.trim().isEmpty())
                && ((code == null) || code.trim().isEmpty())) {
            throw new AuthException(AuthErrorCode.MISSING_OLD_PASSWORD_OR_CODE_FIELD);
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new AuthException(AuthErrorCode.MISSING_NEW_PASSWORD_FIELD);
        }

        if (oldPassword != null && !oldPassword.trim().isEmpty() && !validateField.validPassword(oldPassword)) {
            throw new AuthException(AuthErrorCode.OLD_PASSWORD_INVALID);
        }

        if (!validateField.validPassword(newPassword)) {
            throw new AuthException(AuthErrorCode.NEW_PASSWORD_INVALID);
        }
    }
}
