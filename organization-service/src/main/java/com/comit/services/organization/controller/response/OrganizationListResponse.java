package com.comit.services.organization.controller.response;

import com.comit.services.organization.constant.OrganizationErrorCode;
import com.comit.services.organization.model.dto.OrganizationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationListResponse extends BasePagingResponse {
    @JsonProperty(value = "organizations")
    private List<OrganizationDto> organizationDtos;

    public OrganizationListResponse(OrganizationErrorCode errorCode, List<OrganizationDto> organizationDtos) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.organizationDtos = organizationDtos;
    }

    public OrganizationListResponse(OrganizationErrorCode errorCode, List<OrganizationDto> organizationDtos,
                                    int currentPage, long totalItems, int totalPages) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.organizationDtos = organizationDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
