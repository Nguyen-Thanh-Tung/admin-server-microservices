package com.comit.services.employee.client.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaEmployeeTimeDtoClient {
    private Integer id;
    private Integer employeeId;

    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDtoClient areaRestriction;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;
}

