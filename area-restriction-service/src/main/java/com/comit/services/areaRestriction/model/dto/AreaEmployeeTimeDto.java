package com.comit.services.areaRestriction.model.dto;

import com.comit.services.areaRestriction.client.data.EmployeeDto;
import com.comit.services.areaRestriction.model.entity.AreaEmployeeTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class AreaEmployeeTimeDto extends BaseModelDto {
    private EmployeeDto employee;

    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;
}
