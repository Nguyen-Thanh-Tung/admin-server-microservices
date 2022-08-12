package com.comit.services.employee.client;

import com.comit.services.employee.client.response.CountEmployeeResponse;
import com.comit.services.employee.client.response.EmployeeResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmployeeClientImpl implements EmployeeClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://employee-service";

    public EmployeeClientImpl(WebClient.Builder builder) {

        this.webClientBuilder = builder;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public EmployeeResponse getEmployee(String token, Integer id) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/employees/" + id)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> EmployeeResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public CountEmployeeResponse getNumberEmployeeOfLocation(String token, Integer locationId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/employees/location/" + locationId + "/number-employee")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> CountEmployeeResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
