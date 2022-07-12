package com.comit.location.client.response;

import com.comit.location.controller.response.BaseResponse;
import com.comit.location.model.entity.Organization;
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
