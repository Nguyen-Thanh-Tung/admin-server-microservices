package com.comit.services.employee.client.response;

import com.comit.services.employee.controller.response.BasePagingResponse;
import com.comit.services.employee.model.entity.AreaRestriction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AreaRestrictionListResponse extends BasePagingResponse {
    @JsonProperty(value = "area_restrictions")
    private List<AreaRestriction> areaRestrictions;

    public AreaRestrictionListResponse(
            int code,
            String message,
            List<AreaRestriction> areaRestrictions,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.code = code;
        this.message = message;
        this.areaRestrictions = areaRestrictions;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
