package com.comit.services.employee.client.data;

import com.comit.services.employee.model.dto.BaseEmployeeDto;
import com.comit.services.employee.model.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaEmployeeTimeDtoClient {
    private Integer id;
    @JsonProperty(value = "employee")
    private BaseEmployeeDto employee;

    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDtoClient areaRestriction;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;
}

