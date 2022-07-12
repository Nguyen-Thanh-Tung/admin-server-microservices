package com.comit.organization.controller.response;

import com.comit.organization.constant.OrganizationErrorCode;
import com.comit.organization.model.dto.OrganizationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponse extends BaseResponse {
    @JsonProperty(value = "organization")
    private OrganizationDto organizationDto;

    public OrganizationResponse(OrganizationErrorCode errorCode, OrganizationDto organizationDto) {
        super(errorCode);
        this.organizationDto = organizationDto;
    }
}
