package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.model.dto.BaseUserDto;
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
public class UserListBaseResponse extends BaseResponse {
    @JsonProperty(value = "users")
    private List<BaseUserDto> users;

    public UserListBaseResponse(UserErrorCode errorCode, List<BaseUserDto> userDtos) {
        super(errorCode);
        this.users = userDtos;
    }
}
