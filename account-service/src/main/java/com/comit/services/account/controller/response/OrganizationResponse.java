package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.model.dto.OrganizationDto;
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

    public OrganizationResponse(UserErrorCode userErrorCode, OrganizationDto organizationDto) {
        this.organizationDto = organizationDto;
        this.code = userErrorCode.getCode();
        this.message = userErrorCode.getMessage();
    }
}
