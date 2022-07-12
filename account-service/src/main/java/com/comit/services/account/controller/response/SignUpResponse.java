package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse extends BaseResponse {
    @JsonProperty(value = "user")
    private UserDto userDto;

    public SignUpResponse(UserErrorCode errorCode, UserDto userDto) {
        super(errorCode);
        this.userDto = userDto;
    }
}
