package com.comit.organization.controller.response;

import com.comit.organization.constant.OrganizationErrorCode;
import com.comit.organization.model.dto.OrganizationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganizationListResponse extends BaseResponse {
    @JsonProperty(value = "organizations")
    private List<OrganizationDto> organizationDtos;

    public OrganizationListResponse(OrganizationErrorCode errorCode, List<OrganizationDto> organizationDtos) {
        super(errorCode);
        this.organizationDtos = organizationDtos;
    }
}
