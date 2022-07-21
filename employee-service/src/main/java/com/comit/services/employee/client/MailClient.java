package com.comit.services.employee.client;

import com.comit.services.employee.client.request.MailRequest;
import com.comit.services.employee.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-service")
public interface MailClient {
	@PostMapping("/mails/qrcode")
    ResponseEntity<BaseResponse> sendQrCodeMail(@RequestBody MailRequest mailRequest);
}
