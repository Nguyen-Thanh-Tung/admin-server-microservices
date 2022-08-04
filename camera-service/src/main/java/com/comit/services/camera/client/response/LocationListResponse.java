package com.comit.services.camera.client.response;

import com.comit.services.camera.client.data.LocationDto;
import com.comit.services.camera.controller.response.BasePagingResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationListResponse extends BasePagingResponse {
    @JsonProperty("locations")
    private List<LocationDto> locations;


    public LocationListResponse(
            int code,
            String message,
            List<LocationDto> locations,
            int currentPage,
            long totalItems,
            int totalPages) {
        this.code = code;
        this.message = message;
        this.locations = locations;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
