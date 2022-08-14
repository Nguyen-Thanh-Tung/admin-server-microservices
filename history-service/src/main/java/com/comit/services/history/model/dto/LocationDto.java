package com.comit.services.history.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {
    private Integer id;
    private String name;
    private String code;
    private String type;
    @JsonProperty("organization_id")
    private Integer organizationId;
}
