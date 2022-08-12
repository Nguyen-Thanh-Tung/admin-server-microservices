package com.comit.services.account.server.business;

import com.comit.services.account.client.dto.RoleDto;
import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.client.request.AddUserRequest;
import com.comit.services.account.client.request.LockOrUnlockRequest;
import com.comit.services.account.client.request.UpdateRoleForUserRequest;
import com.comit.services.account.server.model.User;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.organization.client.dto.OrganizationDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

public interface UserBusiness {
    // For user services
    List<UserDto> getAllUser();

    UserDto getUser(int id);

    UserDto addUser(AddUserRequest addUserRequest);

    boolean deleteUser(int id);

    UserDto addRoleToUser(int id, Set<String> roles);

    UserDto updateRoleUser(int id, UpdateRoleForUserRequest updateRoleForUserRequest);

    UserDto updateUser(int id, HttpServletRequest servletRequest);

    UserDto lockOrUnlockUser(int id, LockOrUnlockRequest request);

    UserDto uploadAvatar(int id, HttpServletRequest httpServletRequest);

    int getNumberAccount();

    List<UserDto> getUsersByOrganizationId(int organizationId);

    LocationDto getLocationOfCurrentUser();

    OrganizationDto getOrganizationOfCurrentUser();

    UserDto getCurrentUser();

    List<UserDto> getUsersOfCurrentUser();

    List<RoleDto> getRolesOfCurrentUser();

    UserDto convertUserToUserDto(User user);

    int getNumberUserOfLocation(Integer locationId);
}
