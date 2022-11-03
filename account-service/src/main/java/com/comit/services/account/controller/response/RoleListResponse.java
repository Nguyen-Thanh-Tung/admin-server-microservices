package com.comit.services.account.controller.response;

import com.comit.services.account.constant.RoleErrorCode;
import com.comit.services.account.model.dto.RoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleListResponse extends BaseResponse {
    @JsonProperty(value = "roles")
    private List<RoleDto> roleDtos;

    public RoleListResponse(RoleErrorCode errorCode, List<RoleDto> roleDtos) {
        super(errorCode);
        this.roleDtos = roleDtos;
    }
}
