package com.comit.services.employee.client.data;

import com.comit.services.employee.model.dto.BaseModelDto;
import com.comit.services.employee.model.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaEmployeeTimeDto extends BaseModelDto {
    private Integer employeeId;

    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;
}

