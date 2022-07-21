package com.comit.services.organization.client.response;

import com.comit.services.organization.controller.response.BaseResponse;
import com.comit.services.organization.model.entity.Location;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse extends BaseResponse {
    @JsonProperty(value = "location")
    private Location location;

    public LocationResponse(int code, String message, Location location) {
        super(code, message);
        this.location = location;
    }
}
