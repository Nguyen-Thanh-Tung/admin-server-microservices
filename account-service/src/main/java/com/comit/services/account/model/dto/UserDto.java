package com.comit.services.account.model.dto;

import com.comit.services.account.client.data.LocationDtoClient;
import com.comit.services.account.client.data.MetadataDtoClient;
import com.comit.services.account.client.data.OrganizationDtoClient;
import com.comit.services.account.model.entity.Role;
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
public class UserDto extends BaseModelDto {
    private String username;
    private String fullname;
    private String email;
    private String status;

    @JsonIncludeProperties({"id", "name"})
    private Set<RoleDto> roles;

    @JsonProperty("parent_user")
    @JsonIncludeProperties({"id", "fullname", "roles"})
    private UserDto parent;

    @JsonIncludeProperties({"id", "path", "type"})
    private MetadataDtoClient avatar;

    @JsonIncludeProperties({"id", "name"})
    private OrganizationDtoClient organization;

    @JsonIncludeProperties({"id", "name"})
    private LocationDtoClient location;

    public String toString(Set<Role> roles) {
        StringBuilder rolesStr = new StringBuilder();
        roles.forEach(role -> {
            rolesStr.append(role.getName()).append(", ");
        });
        return String.valueOf(rolesStr);
    }
}
