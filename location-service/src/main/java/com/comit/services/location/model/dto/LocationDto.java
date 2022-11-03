package com.comit.services.location.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto extends BaseModelDto {
    private String name;
    private String code;
    private String type;

    @JsonProperty(value = "organization_id")
    private Integer organizationId;

    @JsonProperty(value = "number_camera")
    private Integer numberCamera;

    @JsonProperty(value = "number_employee")
    private Integer numberEmployees;
}
