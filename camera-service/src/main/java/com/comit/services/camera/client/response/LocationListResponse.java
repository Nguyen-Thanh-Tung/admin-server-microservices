package com.comit.services.camera.client.response;

import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.controller.response.BasePagingResponse;
import com.comit.services.camera.model.entity.Location;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationListResponse extends BasePagingResponse {
    @JsonProperty("locations")
    private List<Location> locations;


    public LocationListResponse(
            int code,
            String message,
            List<Location> locations,
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
