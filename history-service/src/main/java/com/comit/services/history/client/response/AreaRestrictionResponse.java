package com.comit.services.history.client.response;

import com.comit.services.history.client.data.AreaRestrictionDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRestrictionResponse extends BaseResponseClient {
    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDtoClient areaRestriction;

    public AreaRestrictionResponse(int code, String message, AreaRestrictionDtoClient areaRestriction) {
        this.code = code;
        this.message = message;
        this.areaRestriction = areaRestriction;
    }
}
