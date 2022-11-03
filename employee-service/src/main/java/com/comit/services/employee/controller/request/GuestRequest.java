package com.comit.services.employee.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestRequest {
    private String name;
    private String email;
    private String phone;
    private String image;
    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("area_restriction_id")
    private Integer areaRestrictionId;
}
