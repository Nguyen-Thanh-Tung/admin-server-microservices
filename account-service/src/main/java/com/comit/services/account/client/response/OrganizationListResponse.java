package com.comit.services.account.client.response;

import com.comit.services.account.controller.response.BaseResponse;
import com.comit.services.account.model.entity.Organization;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganizationListResponse extends BaseResponse {
    @JsonProperty(value = "organizations")
    private List<Organization> organizations;

    public OrganizationListResponse(int code, String message) {
        super(code, message);
    }
}
