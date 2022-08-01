package com.comit.services.account.business;

import com.comit.services.account.model.dto.RoleDto;
import com.comit.services.account.model.dto.UserDto;

import java.util.List;

public interface RoleBusiness {
    List<RoleDto> getAllRole();

    boolean isCurrentUserSuperAdmin();

    List<UserDto> getUsersOfRole(Integer roleId);

    RoleDto getRoleByName(String roleName);
}
