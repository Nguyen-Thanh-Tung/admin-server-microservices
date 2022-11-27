package com.comit.services.camera.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCameraDto extends BaseModelDto {
    @JsonProperty(value = "ip_address")
    private String ipAddress;
    private String name;
    private String type;
    private String status;
    private String taken;

    @JsonProperty("location_id")
    private Integer locationId;

    @JsonProperty(value = "area_restriction_id")
    private Integer areaRestrictionId;

    private String polygons;
}
