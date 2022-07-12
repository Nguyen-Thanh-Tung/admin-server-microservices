package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends BaseResponse {
    @JsonProperty(value = "user")
    private UserDto userDto;

    private String token;

    public LoginResponse(UserErrorCode errorCode, UserDto userDto, String token) {
        super(errorCode);
        this.userDto = userDto;
        this.token = token;
    }
}
