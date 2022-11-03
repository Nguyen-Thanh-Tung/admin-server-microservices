package com.comit.services.feature.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckRoleResponseClient extends BaseResponseClient {
    @JsonProperty("has_role")
    private boolean isSuperAdmin;
}
