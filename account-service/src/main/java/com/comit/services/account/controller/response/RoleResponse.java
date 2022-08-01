package com.comit.services.account.controller.response;

import com.comit.services.account.constant.RoleErrorCode;
import com.comit.services.account.model.dto.RoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse extends BaseResponse {
    @JsonProperty("role")
    private RoleDto roleDto;

    public RoleResponse(RoleErrorCode roleErrorCode, RoleDto roleDto) {
        this.code = roleErrorCode.getCode();
        this.message = roleErrorCode.getMessage();
        this.roleDto = roleDto;
    }
}
