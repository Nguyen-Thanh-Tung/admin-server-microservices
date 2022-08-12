package com.comit.services.feature.client;

import com.comit.services.feature.client.request.FeatureRequest;
import com.comit.services.feature.client.response.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FeatureClientImpl implements FeatureClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://feature-service";

    public FeatureClientImpl(WebClient.Builder builder) {
        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public BaseResponse addFeature(String token, FeatureRequest featureRequest) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/features")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .body(featureRequest, FeatureRequest.class)
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
