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
public class UserDto extends BaseModelDto {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String status;
    private String code;
}
