package com.comit.services.organization.controller.response;

import com.comit.services.organization.constant.OrganizationErrorCode;
import com.comit.services.organization.model.dto.OrganizationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponse extends BaseResponse {
    @JsonProperty(value = "organization")
    private OrganizationDto organizationDto;

    public OrganizationResponse(OrganizationErrorCode errorCode, OrganizationDto organizationDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.organizationDto = organizationDto;
    }
}
