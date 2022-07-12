package com.comit.services.account.controller.response;

import com.comit.services.account.model.entity.Location;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationListResponse extends BasePagingResponse {
    @JsonProperty("locations")
    private List<Location> locations;

    public LocationListResponse(int code, String message) {
        super(code, message);
    }
}
