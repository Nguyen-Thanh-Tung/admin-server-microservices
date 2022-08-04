package com.comit.services.userLog.client.response;

import com.comit.services.userLog.client.data.UserDto;
import com.comit.services.userLog.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseResponse {
    @JsonProperty(value = "user")
    private UserDto userDto;

    public UserResponse(int code, String message, UserDto userDto) {
        this.code = code;
        this.message = message;
        this.userDto = userDto;
    }
}
