package com.comit.services.account.client.response;

import com.comit.services.account.controller.response.BaseResponse;
import com.comit.services.account.model.entity.Organization;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponse extends BaseResponse {
    @JsonProperty(value = "organization")
    private Organization organization;


    public OrganizationResponse(int code, String message) {
        super(code, message);
    }
}
