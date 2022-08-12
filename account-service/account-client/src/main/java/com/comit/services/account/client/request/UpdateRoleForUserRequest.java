package com.comit.services.account.client.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateRoleForUserRequest {
    private Set<String> roles;

    @Override
    public String toString() {
        return "UpdateRoleForUserRequest{" +
                "roles=" + roles +
                '}';
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
