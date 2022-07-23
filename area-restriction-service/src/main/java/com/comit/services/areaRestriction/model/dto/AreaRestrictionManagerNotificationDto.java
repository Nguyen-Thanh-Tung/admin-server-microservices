package com.comit.services.areaRestriction.model.dto;

import com.comit.services.areaRestriction.model.entity.Employee;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRestrictionManagerNotificationDto extends BaseModelDto {

    @JsonIncludeProperties(value = {"id", "name", "code", "email"})
    private Employee manager;

    @JsonProperty(value = "time_skip")
    private Integer timeSkip;
}
