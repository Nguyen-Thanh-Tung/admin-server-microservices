package com.comit.services.organization.client.response;

import com.comit.services.organization.client.data.LocationDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationListResponseClient extends BaseResponseClient {
    @JsonProperty("locations")
    private List<LocationDtoClient> location;


    public LocationListResponseClient(
            int code,
            String message,
            List<LocationDtoClient> locationDtoClients) {
        this.code = code;
        this.message = message;
        this.location = locationDtoClients;
    }
}
