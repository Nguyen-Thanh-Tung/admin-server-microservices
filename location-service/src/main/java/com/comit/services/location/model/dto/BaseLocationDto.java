package com.comit.services.location.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseLocationDto extends BaseModelDto {
    private String name;
    private String code;
    private String type;

    @JsonProperty(value = "organization_id")
    private Integer organizationId;
}
