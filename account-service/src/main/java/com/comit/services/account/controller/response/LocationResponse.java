package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.model.dto.LocationDto;
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
    private LocationDto locationDto;

    public LocationResponse(UserErrorCode userErrorCode, LocationDto locationDto) {
       this.locationDto = locationDto;
       this.code = userErrorCode.getCode();
       this.message = userErrorCode.getMessage();
    }
}
