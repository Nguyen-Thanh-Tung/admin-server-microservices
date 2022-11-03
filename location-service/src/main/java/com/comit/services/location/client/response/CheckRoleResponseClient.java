package com.comit.services.location.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckRoleResponseClient extends BaseResponseClient {
    @JsonProperty(value = "has_role")
    private Boolean hasRole;

    public CheckRoleResponseClient(int code, String message, Boolean hasRole) {
        this.code = code;
        this.message = message;
        this.hasRole = hasRole;
    }
}
