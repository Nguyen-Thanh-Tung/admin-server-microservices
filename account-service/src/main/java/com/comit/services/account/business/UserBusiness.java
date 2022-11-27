package com.comit.services.account.business;

import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.model.dto.BaseUserDto;
import com.comit.services.account.model.dto.RoleDto;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.model.entity.Role;
import com.comit.services.account.model.entity.User;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface UserBusiness {
    // For user services
    Page<User> getAllUser(int size, int page, String search, String status);

    List<UserDto> getAllUser(List<User> users);

    UserDto getUser(int id);

    BaseUserDto getUserBase(int id);

    UserDto addUser(AddUserRequest addUserRequest);

    boolean deleteUser(int id);

    UserDto addRoleToUser(int id, Set<String> roles);

    UserDto updateUser(int id, HttpServletRequest servletRequest);

    UserDto lockOrUnlockUser(int id, LockOrUnlockRequest request);

    UserDto uploadAvatar(int id, HttpServletRequest httpServletRequest);

    int getNumberAccount();

    int getNumberUserOfOrganization(int organizationId);

    int getNumberAllUserOfOrganization(int organizationId);

    BaseUserDto getCurrentUser();

    List<BaseUserDto> getUsersOfCurrentUser();

    List<RoleDto> getRolesOfCurrentUser();

    UserDto convertUserToUserDto(User user);

    int getNumberUserOfLocation(Integer locationId);

    int getNumberUserOfRoles(String roleIds);

    int getNumberOrganizationOfRoles(String roleIds);

    boolean resendCode(Integer id);

    boolean checkRole(String roleNeedCheck);

    boolean checkRoleAddAndUpdateUser(User user, String roleNeedCheck);

    Set<Role> convertRoleFromStringToArray(User user, String roleStr, String locationIdStr);
}
