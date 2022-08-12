package com.comit.services.history.client;

import com.comit.services.history.client.response.CountNotificationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HistoryClientImpl implements HistoryClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://history-service";

    public HistoryClientImpl(WebClient.Builder builder) {
        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public CountNotificationResponse getNumberNotificationOfAreaRestriction(String token, int areaRestrictionId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/notification-histories/area-restriction/" + areaRestrictionId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> CountNotificationResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
