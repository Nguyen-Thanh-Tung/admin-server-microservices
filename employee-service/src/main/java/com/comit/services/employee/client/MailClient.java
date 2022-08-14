package com.comit.services.employee.client;

import com.comit.services.employee.client.request.MailRequestClient;
import com.comit.services.employee.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "mail-service")
public interface MailClient {
    @PostMapping("/mails/qrcode")
    ResponseEntity<BaseResponse> sendQrCodeMail(@RequestHeader String token, @RequestBody MailRequestClient mailRequestClient);
}
