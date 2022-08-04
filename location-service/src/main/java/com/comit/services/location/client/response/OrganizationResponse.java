package com.comit.services.location.client.response;

import com.comit.services.location.client.data.OrganizationDto;
import com.comit.services.location.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponse extends BaseResponse {
    @JsonProperty(value = "organization")
    private OrganizationDto organizationDto;

    public OrganizationResponse(int code, String message, OrganizationDto organizationDto) {
        this.code = code;
        this.message = message;
        this.organizationDto = organizationDto;
    }
}
