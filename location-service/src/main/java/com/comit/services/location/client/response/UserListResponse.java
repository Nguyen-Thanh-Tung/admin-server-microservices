package com.comit.services.location.client.response;

import com.comit.services.location.client.data.UserDto;
import com.comit.services.location.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListResponse extends BaseResponse {
    @JsonProperty(value = "users")
    private List<UserDto> userDtos;

    public UserListResponse(int code, String message, List<UserDto> userDtos) {
        this.code = code;
        this.message = message;
        this.userDtos = userDtos;
    }
}
