package com.comit.services.camera.client.response;

import com.comit.services.camera.client.data.AreaRestrictionDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRestrictionResponseClient extends BaseResponseClient {
    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDtoClient areaRestriction;

    public AreaRestrictionResponseClient(int code, String message, AreaRestrictionDtoClient areaRestriction) {
        this.code = code;
        this.message = message;
        this.areaRestriction = areaRestriction;
    }
}
