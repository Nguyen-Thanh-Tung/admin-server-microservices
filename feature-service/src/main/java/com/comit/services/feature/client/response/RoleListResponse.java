package com.comit.services.feature.client.response;

import com.comit.services.feature.client.data.RoleDto;
import com.comit.services.feature.controller.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleListResponse extends BaseResponse {
    List<RoleDto> roleDtos;
}
