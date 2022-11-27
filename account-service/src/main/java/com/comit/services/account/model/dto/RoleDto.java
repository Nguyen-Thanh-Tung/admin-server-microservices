package com.comit.services.account.model.dto;

import com.comit.services.account.exeption.CommonLogger;
import com.comit.services.account.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.List;
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
            CommonLogger.error(e.getMessage(), e);
            return null;
        }
    }

    public static RoleDto convertRoleToRoleDtoWithModule(Role role, String keyModule) {
        if (role == null) return null;
        RoleDto roleDto = convertRoleToRoleDto(role);
        if (keyModule == null || (role.getName() != null && role.getName().contains(keyModule))) {
            return roleDto;
        }
        return null;
    }

    public static String toString(List<RoleDto> roles) {
        StringBuilder rolesStr = new StringBuilder();
        roles.forEach(role -> {
            rolesStr.append(role.getName()).append(", ");
        });
        return String.valueOf(rolesStr);
    }
}
