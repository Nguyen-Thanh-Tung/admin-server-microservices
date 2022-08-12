package com.comit.services.account.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AddUserRequest {
    private String fullname;
    private Set<String> roles;
    @JsonProperty("organization_id")
    private Integer organizationId;
    private String email;

    @JsonProperty("location_id")
    private Integer locationId;
}
