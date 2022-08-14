package com.comit.services.account.client;

import com.comit.services.account.client.request.MailRequestClient;
import com.comit.services.account.client.response.BaseResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "mail-service")
public interface MailClient {

    @PostMapping("/mails/create-user")
    ResponseEntity<BaseResponseClient> sendMailConfirmCreateUser(@RequestHeader String token, @RequestBody MailRequestClient mailRequestClient);

    @PostMapping("/mails/forget-password")
    ResponseEntity<BaseResponseClient> sendMailForgetPassword(@RequestHeader String token, @RequestBody MailRequestClient mailRequestClient);
}
