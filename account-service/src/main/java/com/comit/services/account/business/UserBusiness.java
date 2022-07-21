package com.comit.services.account.business;

import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.controller.request.UpdateRoleForUserRequest;
import com.comit.services.account.model.dto.LocationDto;
import com.comit.services.account.model.dto.OrganizationDto;
import com.comit.services.account.model.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserBusiness {
    // For user services
    List<UserDto> getAllUser();

    UserDto getUser(int id) throws IOException;

    UserDto addUser(AddUserRequest addUserRequest) throws IOException;

    boolean deleteUser(int id);

    UserDto addRoleToUser(int id, Set<String> roles) throws IOException;

    UserDto updateRoleUser(int id, UpdateRoleForUserRequest updateRoleForUserRequest) throws IOException;

    UserDto updateUser(int id, HttpServletRequest servletRequest) throws IOException;

    UserDto lockOrUnlockUser(int id, LockOrUnlockRequest request) throws IOException;

    UserDto uploadAvatar(int id, HttpServletRequest httpServletRequest) throws IOException;

    int getNumberAccount();

    List<UserDto> getUsersByOrganizationId(int organizationId);

    LocationDto getLocationOfCurrentUser();

    OrganizationDto getOrganizationOfCurrentUser();
}
