package com.comit.services.location.client.response;

import com.comit.services.location.client.data.OrganizationDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganizationListResponseClient extends BaseResponseClient {
    @JsonProperty(value = "organizations")
    private List<OrganizationDtoClient> organizations;

    public OrganizationListResponseClient(int code, String message, List<OrganizationDtoClient> organizations) {
        this.code = code;
        this.message = message;
        this.organizations = organizations;
    }
}
