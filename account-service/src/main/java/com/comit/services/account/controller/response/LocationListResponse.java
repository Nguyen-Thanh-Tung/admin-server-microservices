package com.comit.services.account.controller.response;

import com.comit.services.account.client.data.LocationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationListResponse extends BasePagingResponse {
    @JsonProperty("locations")
    private List<LocationDto> locations;

    public LocationListResponse(int code, String message) {
        super(code, message);
    }
}
