package com.comit.services.account.business;

import com.comit.services.account.model.dto.RoleDto;
import com.comit.services.account.model.entity.Role;
import com.comit.services.account.service.RoleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleBusinessImpl implements RoleBusiness {
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
}
