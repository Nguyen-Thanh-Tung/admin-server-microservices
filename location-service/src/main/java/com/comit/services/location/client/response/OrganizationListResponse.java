package com.comit.services.location.client.response;

import com.comit.services.location.client.data.OrganizationDto;
import com.comit.services.location.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganizationListResponse extends BaseResponse {
    @JsonProperty(value = "organizations")
    private List<OrganizationDto> organizationDtos;

    public OrganizationListResponse(int code, String message, List<OrganizationDto> organizationDtos) {
        this.code = code;
        this.message = message;
        this.organizationDtos = organizationDtos;
    }
}
