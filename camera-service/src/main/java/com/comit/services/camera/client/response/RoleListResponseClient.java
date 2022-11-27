package com.comit.services.camera.client.response;

import com.comit.services.camera.client.data.RoleDtoClient;
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

    public String toString(List<RoleDtoClient> roles) {
        StringBuilder result = new StringBuilder();
        roles.forEach(roleDtoClient -> {
            result.append(roleDtoClient.getName()).append(",");
        });
        return String.valueOf(result);
    }
}
