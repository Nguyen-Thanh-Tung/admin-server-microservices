package com.comit.services.userlog.client;

import com.comit.services.userlog.client.request.UserLogRequest;
import com.comit.services.userlog.client.response.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class UserLogClientImpl implements UserLogClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://user-log-service";

    public UserLogClientImpl(WebClient.Builder builder) {

        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public BaseResponse saveUserLog(String token, UserLogRequest userLogRequest) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/user-logs")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .body(userLogRequest, UserLogRequest.class)
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
