package com.comit.services.employee.client.data;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("fullname")
    private String fullName;
    private String email;
    private String status;
    private String code;

    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("organization_id")
    private Integer organizationId;
}
