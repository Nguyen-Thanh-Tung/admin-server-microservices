package com.comit.services.timeKeeping.client.response;

import com.comit.services.timeKeeping.client.data.LocationDto;
import com.comit.services.timeKeeping.controller.response.BaseResponse;
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
