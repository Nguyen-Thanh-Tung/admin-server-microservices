package com.comit.services.feature.client.response;

import com.comit.services.feature.client.data.RoleDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseClient extends BaseResponseClient {
    @JsonProperty("role")
    private RoleDtoClient role;
}
