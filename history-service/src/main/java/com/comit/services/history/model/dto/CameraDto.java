package com.comit.services.history.model.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraDto {
    private Integer id;
    @JsonProperty(value = "ip_address")
    private String ipAddress;
    private String name;
    private String type;
    private String status;
    private String taken;

    // For time keeping module
    @JsonIncludeProperties(value = {"id", "name", "code", "type"})
    private LocationDto location;

    // For area restriction module
    @JsonIncludeProperties(value = {"id", "name", "code"})
    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;

    @JsonProperty(value = "polygons")
    private String polygons;
}

