package com.comit.services.feature.client.response;

import com.comit.services.feature.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckRoleResponse extends BaseResponse {
    @JsonProperty("has_role")
    private boolean isSuperAdmin;
}
