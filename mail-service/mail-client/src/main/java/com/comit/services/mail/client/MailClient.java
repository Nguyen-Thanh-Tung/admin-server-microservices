package com.comit.services.mail.client;

import com.comit.services.mail.client.request.MailCreateUserRequest;
import com.comit.services.mail.client.request.MailForgetPasswordRequest;
import com.comit.services.mail.client.request.MailQrCodeRequest;
import com.comit.services.mail.client.response.BaseResponse;

public interface MailClient {
    BaseResponse sendMailConfirmCreateUser(String token, MailCreateUserRequest request);

    BaseResponse sendMailForgetPassword(String token, MailForgetPasswordRequest mailRequest);

    BaseResponse sendQrCodeMail(String token, MailQrCodeRequest mailRequest);
}
