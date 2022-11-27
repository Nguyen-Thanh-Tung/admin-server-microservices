package com.comit.services.account.model.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserDto extends BaseModelDto {
    private String username;
    private String fullname;
    private String email;
    private String status;
    @JsonIncludeProperties({"id", "name"})
    private Set<RoleDto> roles;

    @JsonProperty("parent_id")
    private Integer parentId;

    @JsonProperty("avatar_id")
    private Integer avatar;

    @JsonProperty("organization_id")
    private Integer organizationId;

    @JsonProperty("location_id")
    private Integer locationId;
}
