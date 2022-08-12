package com.comit.services.account.server.business;

import com.comit.services.account.client.dto.RoleDto;
import com.comit.services.account.client.dto.UserDto;

import java.util.List;

public interface RoleBusiness {
    List<RoleDto> getAllRole();

    boolean isCurrentUserSuperAdmin();

    List<UserDto> getUsersOfRole(Integer roleId);

    RoleDto getRoleByName(String roleName);
}
