package com.comit.services.account.controller;

import com.comit.services.account.business.RoleBusiness;
import com.comit.services.account.constant.RoleErrorCode;
import com.comit.services.account.controller.response.BaseResponse;
import com.comit.services.account.controller.response.CheckRoleResponse;
import com.comit.services.account.model.dto.RoleDto;
import com.comit.services.account.controller.response.RoleListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
        return new ResponseEntity<>(new RoleListResponse(RoleErrorCode.SUCCESS, roleDtos), HttpStatus.OK);
    }

    @GetMapping("/organization")
    ResponseEntity<BaseResponse> hasPermissionManageOrganization() {
        return new ResponseEntity<>(new CheckRoleResponse(RoleErrorCode.SUCCESS.getCode(), RoleErrorCode.SUCCESS.getMessage(), true), HttpStatus.OK);
    }
}
