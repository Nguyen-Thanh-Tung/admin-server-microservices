package com.comit.services.location.client.response;

import com.comit.services.location.controller.response.BaseResponse;
import com.comit.services.location.model.entity.Organization;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponse extends BaseResponse {
    @JsonProperty(value = "organization")
    private Organization organization;

    public OrganizationResponse(int code, String message, Organization organization) {
        this.code = code;
        this.message = message;
        this.organization = organization;
    }
}
