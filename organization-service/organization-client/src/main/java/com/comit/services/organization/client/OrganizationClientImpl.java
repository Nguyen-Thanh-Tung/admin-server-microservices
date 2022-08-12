package com.comit.services.organization.client;

import com.comit.services.organization.client.request.OrganizationRequest;
import com.comit.services.organization.client.response.OrganizationResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OrganizationClientImpl implements OrganizationClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://organization-service";

    public OrganizationClientImpl(WebClient.Builder builder) {
        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public OrganizationResponse getOrganization(String token, Integer organizationId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/organizations/" + organizationId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class) // just get the response as a JSON string
                .mapNotNull(jsonString -> OrganizationResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public OrganizationResponse getOrganization(String token, String organizationName) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/organizations/name/" + organizationName)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class) // just get the response as a JSON string
                .mapNotNull(jsonString -> OrganizationResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public OrganizationResponse addOrganization(String token, OrganizationRequest organizationRequest) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/organizations")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .body(organizationRequest, OrganizationRequest.class)
                .retrieve()
                .toEntity(String.class) // just get the response as a JSON string
                .mapNotNull(jsonString -> OrganizationResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
