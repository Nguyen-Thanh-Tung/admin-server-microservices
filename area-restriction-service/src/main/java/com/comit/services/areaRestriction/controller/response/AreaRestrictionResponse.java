package com.comit.services.areaRestriction.controller.response;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.model.dto.AreaRestrictionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaRestrictionResponse extends BaseResponse {
    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestrictionDto;

    public AreaRestrictionResponse(AreaRestrictionErrorCode errorCode, AreaRestrictionDto areaRestrictionDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.areaRestrictionDto = areaRestrictionDto;
    }
}
