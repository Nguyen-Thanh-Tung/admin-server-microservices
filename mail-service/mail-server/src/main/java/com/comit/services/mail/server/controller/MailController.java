package com.comit.services.mail.server.controller;

import com.comit.services.mail.client.request.MailCreateUserRequest;
import com.comit.services.mail.client.request.MailForgetPasswordRequest;
import com.comit.services.mail.client.request.MailQrCodeRequest;
import com.comit.services.mail.client.response.BaseResponse;
import com.comit.services.mail.server.business.MailBusiness;
import com.comit.services.mail.server.constant.MailErrorCode;
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
        if (sendQrCodeMailSuccess) {
            return new ResponseEntity<>(new BaseResponse(MailErrorCode.SUCCESS.getCode(), MailErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(MailErrorCode.FAIL.getCode(), MailErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }

    @PostMapping("/create-user")
    public ResponseEntity<BaseResponse> sendCreateUserMail(@RequestBody MailCreateUserRequest mailCreateUserRequest) {
        boolean sendCreateUserMailSuccess = mailBusiness.sendConfirmCreateUserMail(mailCreateUserRequest);
        if (sendCreateUserMailSuccess) {
            return new ResponseEntity<>(new BaseResponse(MailErrorCode.SUCCESS.getCode(), MailErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(MailErrorCode.FAIL.getCode(), MailErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<BaseResponse> sendForgetPasswordMail(@RequestBody MailForgetPasswordRequest mailForgetPasswordRequest) {
        boolean sendForgetPasswordMailSuccess = mailBusiness.sendForgetPasswordMail(mailForgetPasswordRequest);
        if (sendForgetPasswordMailSuccess) {
            return new ResponseEntity<>(new BaseResponse(MailErrorCode.SUCCESS.getCode(), MailErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(MailErrorCode.FAIL.getCode(), MailErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }
}
