package com.comit.services.location.client.response;

import com.comit.services.location.controller.response.BaseResponse;
import com.comit.services.location.model.entity.Organization;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganizationListResponse extends BaseResponse {
    @JsonProperty(value = "organizations")
    private List<Organization> organizations;

    public OrganizationListResponse(int code, String message, List<Organization> organizations) {
        this.code = code;
        this.message = message;
        this.organizations = organizations;
    }
}
