package com.comit.services.location.client;

import com.comit.services.location.client.response.LocationListResponse;
import com.comit.services.location.client.response.LocationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LocationClientImpl implements LocationClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://location-service";

    public LocationClientImpl(WebClient.Builder builder) {
        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public LocationListResponse getLocationsByOrganizationId(String token, int organizationId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/locations/organization/" + organizationId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .map(jsonString -> LocationListResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public LocationResponse getLocationById(String token, Integer locationId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/locations/" + locationId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> LocationResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
