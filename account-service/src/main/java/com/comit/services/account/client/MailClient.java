package com.comit.services.account.client;

import com.comit.services.account.client.request.MailRequest;
import com.comit.services.account.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "mail-service")
public interface MailClient {

    @PostMapping("/mails/create-user")
    ResponseEntity<BaseResponse> sendMailConfirmCreateUser(@RequestHeader String token, @RequestBody MailRequest request);

    @PostMapping("/mails/forget-password")
    ResponseEntity<BaseResponse> sendMailForgetPassword(@RequestHeader String token, @RequestBody MailRequest mailRequest);
}
