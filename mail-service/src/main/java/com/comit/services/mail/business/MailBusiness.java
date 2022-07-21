package com.comit.services.mail.business;

import com.comit.services.mail.controller.request.MailCreateUserRequest;
import com.comit.services.mail.controller.request.MailForgetPasswordRequest;
import com.comit.services.mail.controller.request.MailQrCodeRequest;

public interface MailBusiness {
    boolean sendQrCodeMail(MailQrCodeRequest mailQrCodeRequest);

    boolean sendForgetPasswordMail(MailForgetPasswordRequest mailForgetPasswordRequest);

    boolean sendConfirmCreateUserMail(MailCreateUserRequest mailCreateUserRequest);
}
