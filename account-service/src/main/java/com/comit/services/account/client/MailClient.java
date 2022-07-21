package com.comit.services.account.client;

import com.comit.services.account.client.request.MailRequest;
import com.comit.services.account.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-service")
public interface MailClient {

	@PostMapping("/mails/create-user")
	ResponseEntity<BaseResponse> sendMailConfirmCreateUser(@RequestBody MailRequest request);

	@PostMapping("/mails/forget-password")
	ResponseEntity<BaseResponse> sendMailForgetPassword(@RequestBody MailRequest mailRequest);
}
