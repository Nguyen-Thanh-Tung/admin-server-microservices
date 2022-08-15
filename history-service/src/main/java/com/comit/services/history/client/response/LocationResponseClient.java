package com.comit.services.history.client.response;

import com.comit.services.history.client.data.LocationDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponseClient extends BaseResponseClient {
    @JsonProperty(value = "location")
    private LocationDtoClient locationDtoClient;

    public LocationResponseClient(int code, String message, LocationDtoClient locationDtoClient) {
        this.code = code;
        this.message = message;
        this.locationDtoClient = locationDtoClient;
    }
}
