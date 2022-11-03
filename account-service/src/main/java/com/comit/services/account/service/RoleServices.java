package com.comit.services.account.service;

import com.comit.services.account.model.entity.Role;
import com.comit.services.account.model.entity.User;

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
