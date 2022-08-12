package com.comit.services.camera.client;

import com.comit.services.camera.client.response.CameraResponse;
import com.comit.services.camera.client.response.CountCameraResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CameraClientImpl implements CameraClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://camera-service";

    public CameraClientImpl(WebClient.Builder builder) {

        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public CountCameraResponse getNumberCameraOfAreaRestriction(String token, int areaRestrictionId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/cameras/area-restriction/" + areaRestrictionId + "/number-camera")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> CountCameraResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public CountCameraResponse getNumberCameraOfLocation(String token, Integer locationId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/cameras/location/" + locationId + "/number-camera")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> CountCameraResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public CameraResponse getCamera(String token, Integer id) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/cameras/" + id)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> CameraResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
