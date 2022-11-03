package com.comit.services.areaRestriction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaEmployeeTimeDto extends BaseModelDto {
    @JsonProperty(value = "employee")
    private EmployeeDto employee;

    @JsonProperty(value = "area_restriction")
    private BaseAreaRestrictionDto areaRestriction;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;
}
