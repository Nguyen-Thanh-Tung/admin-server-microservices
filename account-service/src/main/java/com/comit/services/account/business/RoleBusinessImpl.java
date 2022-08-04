package com.comit.services.account.business;

import com.comit.services.account.constant.RoleErrorCode;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.model.dto.RoleDto;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.model.entity.Role;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.service.RoleServices;
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
                roleDtos.add(RoleDto.convertRoleToRoleDto(role));
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
            userDtos.add(userBusiness.convertUserToUserDto(user));
        });
        return userDtos;
    }

    @Override
    public RoleDto getRoleByName(String roleName) {
        Role role = roleServices.findRoleByName(roleName);
        if (role == null) {
            throw new AccountRestApiException(RoleErrorCode.NOT_EXIST_ROLE);
        }
        return RoleDto.convertRoleToRoleDto(role);
    }
}
