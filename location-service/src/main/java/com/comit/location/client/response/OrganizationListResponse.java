package com.comit.location.client.response;

import com.comit.location.controller.response.BaseResponse;
import com.comit.location.model.entity.Organization;
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
