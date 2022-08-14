package com.comit.services.account.model.dto;

import com.comit.services.account.client.data.LocationDtoClient;
import com.comit.services.account.client.data.MetadataDtoClient;
import com.comit.services.account.client.data.OrganizationDtoClient;
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
    private Integer parentId;
    private Integer avatarId;
    private Integer organizationId;
    private Integer locationId;
    @JsonIncludeProperties({"id", "name"})
    private Set<RoleDto> roles;
}
