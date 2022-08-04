package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.client.data.LocationDto;
import com.comit.services.areaRestriction.controller.response.BaseResponse;
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
