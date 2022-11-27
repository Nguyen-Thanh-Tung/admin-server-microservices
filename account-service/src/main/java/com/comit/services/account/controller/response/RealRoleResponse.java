package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealRoleResponse extends BaseResponse {
    private String role;

    public RealRoleResponse(UserErrorCode userErrorCode, String realRole) {
        this.code = userErrorCode.getCode();
        this.message = userErrorCode.getMessage();
        this.role = realRole;
    }
}
