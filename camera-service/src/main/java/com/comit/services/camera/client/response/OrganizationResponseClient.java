package com.comit.services.camera.client.response;

import com.comit.services.camera.client.data.OrganizationDtoClient;
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
