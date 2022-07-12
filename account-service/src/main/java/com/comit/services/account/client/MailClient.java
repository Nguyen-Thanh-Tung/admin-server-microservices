package com.comit.services.account.client;

import com.comit.services.account.client.request.MailRequest;
import com.comit.services.account.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "mail-service")
public interface MailClient {

	@PostMapping("/mails/create-user")
	RequestEntity<BaseResponse> sendMailConfirmCreateUser(@RequestBody MailRequest request);

	@PostMapping("/mails/forget-password")
	RequestEntity<BaseResponse> sendMailForgetPassword(MailRequest mailRequest);
}
