package com.comit.services.mail.client;

import com.comit.services.mail.client.request.MailCreateUserRequest;
import com.comit.services.mail.client.request.MailForgetPasswordRequest;
import com.comit.services.mail.client.request.MailQrCodeRequest;
import com.comit.services.mail.client.response.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MailClientImpl implements MailClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://mail-service";

    public MailClientImpl(WebClient.Builder builder) {
        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public BaseResponse sendMailConfirmCreateUser(String token, MailCreateUserRequest request) {
       return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/mails/create-user")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .body(request, MailCreateUserRequest.class)
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public BaseResponse sendMailForgetPassword(String token, MailForgetPasswordRequest mailRequest) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/mails/forget-password")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .body(mailRequest, MailForgetPasswordRequest.class)
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public BaseResponse sendQrCodeMail(String token, MailQrCodeRequest mailRequest) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/mails/qrcode")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .body(mailRequest, MailQrCodeRequest.class)
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
