package com.comit.services.account.client.response;

import com.comit.services.account.client.data.OrganizationDto;
import com.comit.services.account.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganizationListResponse extends BaseResponse {
    @JsonProperty(value = "organizations")
    private List<OrganizationDto> organizations;

    public OrganizationListResponse(int code, String message, List<OrganizationDto> organizations) {
        this.code = code;
        this.message = message;
        this.organizations = organizations;
    }
}
