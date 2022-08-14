package com.comit.services.feature.client.response;

import com.comit.services.feature.client.data.RoleDtoClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseClient extends BaseResponseClient {
    private RoleDtoClient roleDtoClient;
}
