package com.comit.services.account.model.dto;

import com.comit.services.account.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends BaseModelDto {
    private String name;

    @JsonIncludeProperties({"id", "username"})
    private Set<UserDto> users;

    public static RoleDto convertRoleToRoleDto(Role role) {
        if (role == null) return null;
        try {
            ModelMapper modelMapping = new ModelMapper();
            return modelMapping.map(role, RoleDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
