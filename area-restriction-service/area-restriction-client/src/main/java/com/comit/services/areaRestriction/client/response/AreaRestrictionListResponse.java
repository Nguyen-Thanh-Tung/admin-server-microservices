package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.client.dto.AreaRestrictionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AreaRestrictionListResponse extends BasePagingResponse {
    @JsonProperty(value = "area_restrictions")
    private List<AreaRestrictionDto> areaRestrictionDtos;

    public AreaRestrictionListResponse(
            int errorCode,
            String errorMessage,
            List<AreaRestrictionDto> areaRestrictionDtos,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.setCode(errorCode);
        this.setMessage(errorMessage);
        this.areaRestrictionDtos = areaRestrictionDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
