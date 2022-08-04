package com.comit.services.camera.client.response;

import com.comit.services.camera.client.data.UserDto;
import com.comit.services.camera.controller.response.BaseResponse;
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
