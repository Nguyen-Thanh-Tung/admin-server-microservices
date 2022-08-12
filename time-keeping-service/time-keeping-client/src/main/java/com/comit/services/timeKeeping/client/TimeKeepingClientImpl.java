package com.comit.services.timeKeeping.client;

import com.comit.services.timeKeeping.client.response.BaseResponse;
import com.comit.services.timeKeeping.client.response.ShiftResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TimeKeepingClientImpl implements TimeKeepingClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://time-keeping-service";

    public TimeKeepingClientImpl(WebClient.Builder builder) {

        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public ShiftResponse getShift(String token, int shiftId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/shifts/" + shiftId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> ShiftResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public BaseResponse addShiftsForLocation(String token, Integer locationId) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/shifts/location/" + locationId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public BaseResponse deleteShiftsOfLocation(String token, Integer locationId) {
        return webClientBuilder.build()
                .delete()
                .uri(serviceUrl + "/shifts/location/" + locationId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public BaseResponse addTimeKeepingNotification(String token, Integer locationId) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/time-keeping-notifications/location/" + locationId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public BaseResponse deleteTimeKeepingNotification(String token, Integer locationId) {
        return webClientBuilder.build()
                .delete()
                .uri(serviceUrl + "/time-keeping-notifications/location/" + locationId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
