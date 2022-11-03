package com.comit.services.employee.client.response;

import com.comit.services.employee.client.data.AreaRestrictionDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AreaRestrictionListResponseClient extends BaseResponseClient {
    @JsonProperty(value = "area_restrictions")
    private List<AreaRestrictionDtoClient> areaRestrictions;

    public AreaRestrictionListResponseClient(
            int code,
            String message,
            List<AreaRestrictionDtoClient> areaRestrictions
    ) {
        this.code = code;
        this.message = message;
        this.areaRestrictions = areaRestrictions;
    }
}
