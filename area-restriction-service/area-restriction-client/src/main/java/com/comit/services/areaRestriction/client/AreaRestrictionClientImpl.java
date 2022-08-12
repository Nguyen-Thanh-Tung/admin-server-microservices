package com.comit.services.areaRestriction.client;

import com.comit.services.areaRestriction.client.request.AreaEmployeeTimeListRequest;
import com.comit.services.areaRestriction.client.response.AreaEmployeeTimeListResponse;
import com.comit.services.areaRestriction.client.response.AreaRestrictionResponse;
import com.comit.services.areaRestriction.client.response.BaseResponse;
import com.comit.services.areaRestriction.client.response.NotificationMethodResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AreaRestrictionClientImpl implements AreaRestrictionClient {
    private final WebClient.Builder webClientBuilder;
    private final String serviceUrl = "http://area-restriction-service";

    public AreaRestrictionClientImpl(WebClient.Builder webClient) {

        this.webClientBuilder = webClient;
    }

    private void setHttpHeaders(HttpHeaders httpHeaders, String token) {
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", token);
    }

    @Override
    public AreaRestrictionResponse getAreaRestriction(String token, Integer areaRestrictionId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/area-restrictions/" + areaRestrictionId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> AreaRestrictionResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public BaseResponse deleteManagerOnAllAreaRestriction(String token, Integer managerId) {
        return webClientBuilder.build()
                .delete()
                .uri(serviceUrl + "/area-restrictions/manager/" + managerId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public BaseResponse deleteAreaRestrictionManagerNotificationList(String token, Integer employeeId) {
        return webClientBuilder.build()
                .delete()
                .uri(serviceUrl + "/area-restrictions/manager/" + employeeId + "/notification-manager")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public AreaEmployeeTimeListResponse saveAreaEmployeeTimeList(String token, AreaEmployeeTimeListRequest areaEmployeeTimeListRequest) {
        return webClientBuilder.build()
                .post()
                .uri(serviceUrl + "/area-restrictions/area-employee-times")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> AreaEmployeeTimeListResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public BaseResponse deleteAreaEmployeeTimeList(String token, Integer employeeId) {
        return webClientBuilder.build()
                .delete()
                .uri(serviceUrl + "/area-restrictions/area-employee-times/employee/" + employeeId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> BaseResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public AreaEmployeeTimeListResponse getAreaEmployeeTimesOfEmployee(String token, int employeeId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/area-restrictions/area-employee-times/employee/" + employeeId)
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> AreaEmployeeTimeListResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }

    @Override
    public NotificationMethodResponse getNotificationMethodOfAreaRestriction(String token, Integer areaRestrictionId) {
        return webClientBuilder.build()
                .get()
                .uri(serviceUrl + "/area-restrictions/" + areaRestrictionId + "/notification-method")
                .headers(httpHeaders -> setHttpHeaders(httpHeaders, token))
                .retrieve()
                .toEntity(String.class)
                .mapNotNull(jsonString -> NotificationMethodResponse.convertJsonToObject(jsonString.getBody()))
                .block();
    }
}
