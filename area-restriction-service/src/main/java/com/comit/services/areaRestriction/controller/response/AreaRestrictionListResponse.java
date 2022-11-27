package com.comit.services.areaRestriction.controller.response;

import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.model.dto.AreaRestrictionDto;
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
            AreaRestrictionErrorCode errorCode,
            List<AreaRestrictionDto> areaRestrictionDtos,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.areaRestrictionDtos = areaRestrictionDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public AreaRestrictionListResponse(AreaRestrictionErrorCode errorCode, List<AreaRestrictionDto> areaRestrictionDtos) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.areaRestrictionDtos = areaRestrictionDtos;
    }
}
