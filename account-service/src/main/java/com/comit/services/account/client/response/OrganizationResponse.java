package com.comit.services.account.client.response;

import com.comit.services.account.client.data.OrganizationDto;
import com.comit.services.account.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponse extends BaseResponse {
    @JsonProperty(value = "organization")
    private OrganizationDto organization;

    public OrganizationResponse(int code, String message, OrganizationDto organization) {
        this.organization = organization;
        this.code = code;
        this.message = message;
    }
}
