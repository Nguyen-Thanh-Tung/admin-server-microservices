package com.comit.services.account.controller;

import com.comit.services.account.business.RoleBusiness;
import com.comit.services.account.constant.RoleErrorCode;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.response.*;
import com.comit.services.account.model.dto.RoleDto;
import com.comit.services.account.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {
    @Autowired
    RoleBusiness roleBusiness;

    /**
     * Get all role can manage
     * Super admin show all role of admin
     * Admin show all role of user
     *
     * @return RoleListResponse
     */
    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllRole() {
        List<RoleDto> roleDtos = roleBusiness.getAllRole();
        return new ResponseEntity<>(new RoleListResponse(RoleErrorCode.SUCCESS, roleDtos), HttpStatus.OK);
    }

    @GetMapping("/organization")
    ResponseEntity<BaseResponse> hasPermissionManageOrganization() {
        return new ResponseEntity<>(new CheckRoleResponse(RoleErrorCode.SUCCESS.getCode(), RoleErrorCode.SUCCESS.getMessage(), true), HttpStatus.OK);
    }

    @GetMapping("/location/type/{type}")
    ResponseEntity<BaseResponse> hasPermissionManagerLocation(@PathVariable String type) {
        return new ResponseEntity<>(new CheckRoleResponse(RoleErrorCode.SUCCESS.getCode(), RoleErrorCode.SUCCESS.getMessage(), true), HttpStatus.OK);
    }

    @GetMapping("/is-super-admin")
    ResponseEntity<BaseResponse> isCurrentUserSuperAdmin() {
        boolean isCurrentUserSuperAdmin = roleBusiness.isCurrentUserSuperAdmin();
        return new ResponseEntity<>(new CheckRoleResponse(RoleErrorCode.SUCCESS.getCode(), RoleErrorCode.SUCCESS.getMessage(), isCurrentUserSuperAdmin), HttpStatus.OK);
    }

    @GetMapping("/{roleId}/users")
    ResponseEntity<BaseResponse> getUsersOfRole(@PathVariable Integer roleId) {
        List<UserDto> userDtos = roleBusiness.getUsersOfRole(roleId);
        return new ResponseEntity<>(new UserListResponse(UserErrorCode.SUCCESS, userDtos), HttpStatus.OK);
    }

    @GetMapping("/name/{roleName}")
    ResponseEntity<BaseResponse> getRoleByName(@PathVariable String roleName) {
        RoleDto roleDto = roleBusiness.getRoleByName(roleName);
        return new ResponseEntity<>(new RoleResponse(RoleErrorCode.SUCCESS, roleDto), HttpStatus.OK);
    }
}
