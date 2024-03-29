package com.comit.services.feature.client.response;

import com.comit.services.feature.client.data.RoleDtoClient;
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
public class RoleListResponseClient extends BaseResponseClient {
    @JsonProperty("roles")
    List<RoleDtoClient> roles;
}
