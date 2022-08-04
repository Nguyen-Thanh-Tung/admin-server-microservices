package com.comit.services.history.client.response;

import com.comit.services.history.client.data.AreaRestrictionDto;
import com.comit.services.history.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRestrictionResponse extends BaseResponse {
    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;

    public AreaRestrictionResponse(int code, String message, AreaRestrictionDto areaRestriction) {
        this.code = code;
        this.message = message;
        this.areaRestriction = areaRestriction;
    }
}
