package com.comit.location.controller.response;

import com.comit.location.constant.LocationErrorCode;
import com.comit.location.model.dto.LocationDto;
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
            LocationErrorCode errorCode,
            List<LocationDto> locationDtos,
            int currentPage,
            long totalItems,
            int totalPages) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.locationDtos = locationDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
