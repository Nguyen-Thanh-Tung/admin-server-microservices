package com.comit.services.account.server.controller;

import com.comit.services.account.client.dto.RoleDto;
import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.client.response.*;
import com.comit.services.account.server.business.RoleBusiness;
import com.comit.services.account.server.constant.RoleErrorCode;
import com.comit.services.account.server.constant.UserErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<>(new RoleListResponse(RoleErrorCode.SUCCESS.getCode(), RoleErrorCode.SUCCESS.getMessage(), roleDtos), HttpStatus.OK);
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
    ResponseEntity<UserListResponse> getUsersOfRole(@PathVariable Integer roleId) {
        List<UserDto> userDtos = roleBusiness.getUsersOfRole(roleId);
        return new ResponseEntity<>(new UserListResponse(UserErrorCode.SUCCESS.getCode(), UserErrorCode.SUCCESS.getMessage(), userDtos), HttpStatus.OK);
    }

    @GetMapping("/name/{roleName}")
    ResponseEntity<BaseResponse> getRoleByName(@PathVariable String roleName) {
        RoleDto roleDto = roleBusiness.getRoleByName(roleName);
        return new ResponseEntity<>(new RoleResponse(RoleErrorCode.SUCCESS.getCode(), RoleErrorCode.SUCCESS.getMessage(), roleDto), HttpStatus.OK);
    }
}
