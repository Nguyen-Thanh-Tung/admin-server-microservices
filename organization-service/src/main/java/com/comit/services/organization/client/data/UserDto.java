package com.comit.services.organization.client.data;

import com.comit.services.organization.model.dto.BaseModelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("fullname")
    private String fullName;
    private String email;
    private String status;
    private String code;
}
