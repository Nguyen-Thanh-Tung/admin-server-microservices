package com.comit.services.account.server.service;

import com.comit.services.account.server.model.Role;
import com.comit.services.account.server.model.User;

import java.util.List;

public interface RoleServices {
    boolean existsByName(String role);

    List<Role> getAllRole();

    Role findRoleByName(String role);

    Role addRole(Role role);

    void saveRoles(List<Role> roles);

    boolean hasPermissionManageRole(User currentUser, String roleName);

    boolean isCurrentUserSuperAdmin();

    Role getRole(int roleId);

    void addFeature(String moduleName);
}
