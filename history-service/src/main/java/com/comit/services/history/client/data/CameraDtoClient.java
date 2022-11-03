package com.comit.services.history.client.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CameraDtoClient {
    private Integer id;
    @JsonProperty(value = "ip_address")
    private String ipAddress;
    private String name;
    private String type;
    private String status;
    private String taken;

    // For time keeping module
    @JsonProperty("location_id")
    private Integer locationId;

    // For area restriction module
    @JsonProperty("area_restriction_id")
    private Integer areaRestrictionId;
}

