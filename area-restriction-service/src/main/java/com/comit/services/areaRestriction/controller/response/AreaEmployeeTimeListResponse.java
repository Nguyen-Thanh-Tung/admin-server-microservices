package com.comit.services.areaRestriction.controller.response;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.model.dto.AreaEmployeeTimeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaEmployeeTimeListResponse extends BaseResponse {
    @JsonProperty("area_employee_times")
    List<AreaEmployeeTimeDto> areaEmployeeTimeDtos;

    public AreaEmployeeTimeListResponse(AreaRestrictionErrorCode errorCode, List<AreaEmployeeTimeDto> areaEmployeeTimeDtos) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.areaEmployeeTimeDtos = areaEmployeeTimeDtos;
    }
}
