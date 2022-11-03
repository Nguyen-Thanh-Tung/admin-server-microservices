package com.comit.services.location.client.response;

import com.comit.services.location.client.data.OrganizationDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponseClient extends BaseResponseClient {
    @JsonProperty(value = "organization")
    private OrganizationDtoClient organization;

    public OrganizationResponseClient(int code, String message, OrganizationDtoClient organization) {
        this.code = code;
        this.message = message;
        this.organization = organization;
    }
}
