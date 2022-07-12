package com.comit.organization.client.response;

import com.comit.organization.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckRoleResponse extends BaseResponse {
    @JsonProperty(value = "has_role")
    private Boolean hasRole;

    public CheckRoleResponse(int code, String message, Boolean hasRole) {
        super(code, message);
        this.hasRole = hasRole;
    }
}
