package com.comit.services.organization.client.response;

import com.comit.services.organization.client.data.LocationDto;
import com.comit.services.organization.controller.response.BasePagingResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationListResponse extends BasePagingResponse {
    @JsonProperty("locations")
    private List<LocationDto> locationDtos;


    public LocationListResponse(
            int code,
            String message,
            List<LocationDto> locationDtos,
            int currentPage,
            long totalItems,
            int totalPages) {
        this.code = code;
        this.message = message;
        this.locationDtos = locationDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
