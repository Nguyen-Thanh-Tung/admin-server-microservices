package com.comit.services.account.client;

import com.comit.services.account.client.response.*;

public interface AccountClient {
    UserResponse getCurrentUser(String token);

    CheckRoleResponse isCurrentUserSuperAdmin(String token);

    RoleListResponse getRolesOfCurrentUser(String token);

    UserListResponse getUsersOfRole(String token, Integer roleId);

    RoleResponse getRoleByName(String token, String roleName);

    CheckRoleResponse hasPermissionManageOrganization(String token);

    UserListResponse getUsersByOrganizationId(String token, int organizationId);

    UserListResponse getAllUsersOfCurrentUser(String token);

    UserResponse getUserById(String token, Integer userId);

    CheckRoleResponse hasPermissionManagerLocation(String token, String type);

    CountUserResponse getNumberUserOfLocation(String token, Integer locationId);
}
