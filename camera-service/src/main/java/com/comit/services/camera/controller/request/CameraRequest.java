package com.comit.services.camera.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraRequest {
    @JsonProperty(value = "ip_address")
    private String ipAddress;

    @JsonProperty(value = "location_id")
    private Integer locationId;

    private String name;

    private String type;

    @JsonProperty(value = "area_restriction_id")
    private Integer areaRestrictionId;
}
