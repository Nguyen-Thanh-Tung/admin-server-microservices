package com.comit.services.areaRestriction.client.dto;

import com.comit.services.employee.client.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRestrictionManagerNotificationDto {

    @JsonIncludeProperties(value = {"id", "name", "code", "email"})
    private EmployeeDto manager;

    @JsonProperty(value = "time_skip")
    private Integer timeSkip;
}
