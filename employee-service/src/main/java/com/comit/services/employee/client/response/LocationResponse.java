package com.comit.services.employee.client.response;

import com.comit.services.employee.controller.response.BaseResponse;
import com.comit.services.employee.model.entity.Location;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationResponse extends BaseResponse {
    @JsonProperty(value = "location")
    private Location location;

    public LocationResponse(
            int code,
            String message,
            Location location) {
        this.code = code;
        this.message = message;
        this.location = location;
    }
}
