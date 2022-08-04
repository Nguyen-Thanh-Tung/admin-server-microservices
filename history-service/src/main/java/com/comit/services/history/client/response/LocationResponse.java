package com.comit.services.history.client.response;

import com.comit.services.history.client.data.LocationDto;
import com.comit.services.history.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse extends BaseResponse {
    @JsonProperty(value = "location")
    private LocationDto locationDto;

    public LocationResponse(int code, String message, LocationDto locationDto) {
        this.code = code;
        this.message = message;
        this.locationDto = locationDto;
    }
}
