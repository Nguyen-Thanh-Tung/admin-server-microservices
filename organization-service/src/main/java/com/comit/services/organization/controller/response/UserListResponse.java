package com.comit.services.organization.controller.response;

import com.comit.services.organization.constant.OrganizationErrorCode;
import com.comit.services.organization.model.entity.User;
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
    private List<User> users;

    public UserListResponse(OrganizationErrorCode organizationErrorCode, List<User> users) {
        this.code = organizationErrorCode.getCode();
        this.message = organizationErrorCode.getMessage();
        this.users = users;
    }
}
