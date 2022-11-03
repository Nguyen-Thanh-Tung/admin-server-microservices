package com.comit.services.camera.model.dto;

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
    private String polygons;
    private String taken;

    private LocationDto location;

    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;
}
