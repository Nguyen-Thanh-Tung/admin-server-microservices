package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.client.data.RoleDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleListResponseClient extends BaseResponseClient {
    @JsonProperty(value = "roles")
    private List<RoleDtoClient> roles;

    public RoleListResponseClient(int errorCode, String errorMessage, List<RoleDtoClient> roleDtos) {
        this.code = errorCode;
        this.message = errorMessage;
        this.roles = roleDtos;
    }

}
