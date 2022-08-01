package com.comit.services.feature.client.response;

import com.comit.services.feature.controller.response.BaseResponse;
import com.comit.services.feature.model.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse extends BaseResponse {
    private Role role;
}
