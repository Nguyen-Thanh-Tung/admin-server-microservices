package com.comit.services.camera.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private Integer id;
    private String name;
    private String code;
    private String type;
    @JsonProperty("organization_id")
    private Integer organizationId;
}
