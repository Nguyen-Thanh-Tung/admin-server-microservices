package com.comit.services.location.controller.response;

import com.comit.services.location.constant.LocationErrorCode;
import com.comit.services.location.model.dto.BaseModelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse extends BaseResponse {
    @JsonProperty(value = "location")
    private BaseModelDto location;

    public LocationResponse(LocationErrorCode errorCode, BaseModelDto locationDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.location = locationDto;
    }
}
