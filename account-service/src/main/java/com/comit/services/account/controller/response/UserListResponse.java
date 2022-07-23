package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.model.dto.UserDto;
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
public class UserListResponse extends BaseResponse {
    @JsonProperty(value = "users")
    private List<UserDto> userDtos;

    public UserListResponse(UserErrorCode errorCode, List<UserDto> userDtos) {
        super(errorCode);
        this.userDtos = userDtos;
    }
}
