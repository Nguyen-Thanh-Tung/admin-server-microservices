package com.comit.services.organization.controller.response;

import com.comit.services.organization.client.data.UserDto;
import com.comit.services.organization.constant.OrganizationErrorCode;
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

    public UserListResponse(OrganizationErrorCode organizationErrorCode, List<UserDto> userDtos) {
        this.code = organizationErrorCode.getCode();
        this.message = organizationErrorCode.getMessage();
        this.userDtos = userDtos;
    }
}
