package com.comit.services.location.controller.response;

import com.comit.services.location.constant.LocationErrorCode;
import com.comit.services.location.model.dto.LocationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse extends BaseResponse {
    @JsonProperty(value = "location")
    private LocationDto locationDto;

    public LocationResponse(LocationErrorCode errorCode, LocationDto locationDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.locationDto = locationDto;
    }
}
