package com.comit.services.camera.model.dto;

import com.comit.services.camera.client.data.AreaRestrictionDto;
import com.comit.services.camera.client.data.LocationDto;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraDto extends BaseModelDto {
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
}
