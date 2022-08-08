package com.comit.services.areaRestriction.model.dto;

import com.comit.services.areaRestriction.client.data.EmployeeDto;
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
