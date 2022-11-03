package com.comit.services.location.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequest {
    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "code")
    private String code;

    @JsonProperty(value = "type")
    private String type;
}
