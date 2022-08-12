package com.comit.services.metadata.client;

import com.comit.services.metadata.client.request.MetadataRequest;
import com.comit.services.metadata.client.response.MetadataResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MetadataClientImpl implements MetadataClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://metadata-service";

    public MetadataClientImpl(WebClient.Builder builder) {
        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public MetadataResponse getMetadata(String token, Integer imageId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/metadatas/" + imageId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> MetadataResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public MetadataResponse saveMetadata(String token, MetadataRequest metadataRequest) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/metadatas/save-path")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .body(metadataRequest, MetadataRequest.class)
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> MetadataResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public MetadataResponse saveMetadata(String token, MultipartFile file) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/metadatas/save-file")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .body(file, MultipartFile.class)
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> MetadataResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
