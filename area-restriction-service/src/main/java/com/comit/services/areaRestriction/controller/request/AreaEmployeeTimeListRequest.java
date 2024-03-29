package com.comit.services.areaRestriction.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AreaEmployeeTimeListRequest {
    @JsonProperty("area_employees")
    private String areaEmployees;

    @JsonProperty("employee_id")
    private Integer employeeId;
}
