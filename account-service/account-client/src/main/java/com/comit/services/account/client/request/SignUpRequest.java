package com.comit.services.account.client.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignUpRequest {
    private String fullname;
    private String password;
    private Set<String> roles;
    private int organizationId;
    private String email;

    public SignUpRequest() {
    }

    public SignUpRequest(String fullname, String password, Set<String> roles, int organizationId, String email) {
        this.fullname = fullname;
        this.password = password;
        this.roles = roles;
        this.organizationId = organizationId;
        this.email = email;
    }
}
