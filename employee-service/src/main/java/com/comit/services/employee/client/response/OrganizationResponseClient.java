package com.comit.services.employee.client.response;

import com.comit.services.employee.client.data.OrganizationDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponseClient extends BaseResponseClient {
    @JsonProperty(value = "organization")
    private OrganizationDtoClient organization;

    public OrganizationResponseClient(int code, String message, OrganizationDtoClient organizationDtoClient) {
        this.code = code;
        this.message = message;
        this.organization = organizationDtoClient;
    }
}
