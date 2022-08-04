package com.comit.services.organization.client.response;

import com.comit.services.organization.client.data.LocationDto;
import com.comit.services.organization.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse extends BaseResponse {
    @JsonProperty(value = "location")
    private LocationDto locationDto;

    public LocationResponse(int code, String message, LocationDto locationDto) {
        super(code, message);
        this.locationDto = locationDto;
    }
}
