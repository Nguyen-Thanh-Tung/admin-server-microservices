package com.comit.services.account.client.response;

import com.comit.services.account.client.data.LocationDto;
import com.comit.services.account.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse extends BaseResponse {
    @JsonProperty(value = "location")
    private LocationDto location;

    public LocationResponse(int code, String message, LocationDto location) {
        this.code = code;
        this.message = message;
        this.location = location;
    }
}
