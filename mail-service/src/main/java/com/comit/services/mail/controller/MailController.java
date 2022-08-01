package com.comit.services.mail.controller;

import com.comit.services.mail.business.MailBusiness;
import com.comit.services.mail.constant.MailErrorCode;
import com.comit.services.mail.controller.request.MailCreateUserRequest;
import com.comit.services.mail.controller.request.MailForgetPasswordRequest;
import com.comit.services.mail.controller.request.MailQrCodeRequest;
import com.comit.services.mail.controller.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mails")
public class MailController {
    @Autowired
    private MailBusiness mailBusiness;

    @PostMapping("/qrcode")
    public ResponseEntity<BaseResponse> sendQrCodeMail(@RequestBody MailQrCodeRequest mailQrCodeRequest) {
        boolean sendQrCodeMailSuccess = mailBusiness.sendQrCodeMail(mailQrCodeRequest);
        return new ResponseEntity<>(new BaseResponse(sendQrCodeMailSuccess ? MailErrorCode.SUCCESS : MailErrorCode.FAIL), HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<BaseResponse> sendCreateUserMail(@RequestBody MailCreateUserRequest mailCreateUserRequest) {
        boolean sendCreateUserMailSuccess = mailBusiness.sendConfirmCreateUserMail(mailCreateUserRequest);
        return new ResponseEntity<>(new BaseResponse(sendCreateUserMailSuccess ? MailErrorCode.SUCCESS : MailErrorCode.FAIL), HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<BaseResponse> sendForgetPasswordMail(@RequestBody MailForgetPasswordRequest mailForgetPasswordRequest) {
        boolean sendForgetPasswordMailSuccess = mailBusiness.sendForgetPasswordMail(mailForgetPasswordRequest);
        return new ResponseEntity<>(new BaseResponse(sendForgetPasswordMailSuccess ? MailErrorCode.SUCCESS : MailErrorCode.FAIL), HttpStatus.OK);
    }
}
