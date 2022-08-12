package com.comit.services.account.server.business;

import com.comit.services.account.client.dto.RoleDto;
import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.server.constant.RoleErrorCode;
import com.comit.services.account.server.exeption.AccountRestApiException;
import com.comit.services.account.server.model.Role;
import com.comit.services.account.server.model.User;
import com.comit.services.account.server.service.RoleServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RoleBusinessImpl implements RoleBusiness {
    @Autowired
    UserBusiness userBusiness;
    @Autowired
    RoleServices roleServices;
    @Autowired
    private CommonBusiness commonBusiness;

    @Override
    public List<RoleDto> getAllRole() {
        List<Role> roles = roleServices.getAllRole();
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach(role -> {
            if (roleServices.hasPermissionManageRole(commonBusiness.getCurrentUser(), role.getName())) {
                roleDtos.add(convertRoleToRoleDto(role));
            }
        });

        return roleDtos;
    }

    @Override
    public boolean isCurrentUserSuperAdmin() {
        return roleServices.isCurrentUserSuperAdmin();
    }

    @Override
    public List<UserDto> getUsersOfRole(Integer roleId) {
        Role role = roleServices.getRole(roleId);
        if (role == null) {
            throw new AccountRestApiException(RoleErrorCode.NOT_EXIST_ROLE);
        }
        Set<User> users = role.getUsers();
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            userDtos.add(convertUserToUserDto(user));
        });
        return userDtos;
    }

    private UserDto convertUserToUserDto(User user) {
        if (user == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(user, UserDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public RoleDto getRoleByName(String roleName) {
        Role role = roleServices.findRoleByName(roleName);
        if (role == null) {
            throw new AccountRestApiException(RoleErrorCode.NOT_EXIST_ROLE);
        }
        return convertRoleToRoleDto(role);
    }

    private RoleDto convertRoleToRoleDto(Role role) {
        if (role == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(role, RoleDto.class);
        } catch (Exception e) {
            return null;
        }
    }


}
