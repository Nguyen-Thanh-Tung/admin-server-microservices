package com.comit.services.organization.client.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoClient {
    private Integer id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String status;
    private String code;
}
