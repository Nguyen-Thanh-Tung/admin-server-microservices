package com.comit.services.camera.client.response;

import com.comit.services.camera.client.data.LocationDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationListResponseClient extends BaseResponseClient {
    @JsonProperty("locations")
    private List<LocationDtoClient> locations;

    public LocationListResponseClient(
            int code,
            String message,
            List<LocationDtoClient> locations) {
        this.code = code;
        this.message = message;
        this.locations = locations;
    }
}
