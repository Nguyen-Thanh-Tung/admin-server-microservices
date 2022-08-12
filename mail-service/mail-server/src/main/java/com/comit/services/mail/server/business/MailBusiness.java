package com.comit.services.mail.server.business;


import com.comit.services.mail.client.request.MailCreateUserRequest;
import com.comit.services.mail.client.request.MailForgetPasswordRequest;
import com.comit.services.mail.client.request.MailQrCodeRequest;

public interface MailBusiness {
    boolean sendQrCodeMail(MailQrCodeRequest mailQrCodeRequest);

    boolean sendForgetPasswordMail(MailForgetPasswordRequest mailForgetPasswordRequest);

    boolean sendConfirmCreateUserMail(MailCreateUserRequest mailCreateUserRequest);
}
