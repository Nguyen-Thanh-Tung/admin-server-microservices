package com.comit.services.account.model.dto;

import com.comit.services.account.client.data.LocationDto;
import com.comit.services.account.client.data.MetadataDto;
import com.comit.services.account.client.data.OrganizationDto;
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
    private MetadataDto avatar;

    @JsonIncludeProperties({"id", "name"})
    private OrganizationDto organization;

    @JsonIncludeProperties({"id", "name"})
    private LocationDto location;
}
