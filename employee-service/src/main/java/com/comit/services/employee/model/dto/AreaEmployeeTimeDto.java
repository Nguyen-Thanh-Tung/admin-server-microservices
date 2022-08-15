package com.comit.services.employee.model.dto;

import com.comit.services.employee.client.data.AreaRestrictionDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaEmployeeTimeDto {
    private Integer id;
    @JsonProperty(value = "employee")
    private BaseEmployeeDto employeeDto;

    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;
}

