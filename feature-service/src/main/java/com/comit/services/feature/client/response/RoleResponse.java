package com.comit.services.feature.client.response;

import com.comit.services.feature.client.data.RoleDto;
import com.comit.services.feature.controller.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse extends BaseResponse {
    private RoleDto roleDto;
}
