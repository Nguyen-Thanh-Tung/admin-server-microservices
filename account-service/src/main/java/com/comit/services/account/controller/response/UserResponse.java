package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends BaseResponse {
    @JsonProperty(value = "user")
    private UserDto userDto;

    public UserResponse(UserErrorCode errorCode, UserDto userDto) {
        super(errorCode);
        this.userDto = userDto;
    }
}
