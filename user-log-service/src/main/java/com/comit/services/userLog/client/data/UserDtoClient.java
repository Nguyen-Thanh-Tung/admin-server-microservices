package com.comit.services.userLog.client.data;

import com.comit.services.userLog.model.dto.BaseModelDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoClient extends BaseModelDto {
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String status;
    private String code;
}
