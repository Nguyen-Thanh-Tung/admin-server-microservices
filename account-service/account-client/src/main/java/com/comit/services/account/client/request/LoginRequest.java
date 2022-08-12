package com.comit.services.account.client.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;
}
