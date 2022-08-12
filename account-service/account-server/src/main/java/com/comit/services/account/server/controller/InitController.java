package com.comit.services.account.server.controller;

import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.client.response.BaseResponse;
import com.comit.services.account.client.response.SignUpResponse;
import com.comit.services.account.server.business.AuthBusinessImpl;
import com.comit.services.account.server.constant.UserErrorCode;
import com.comit.services.account.server.service.RoleServices;
import com.comit.services.account.server.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/init")
public class InitController {
    @Autowired
    AuthBusinessImpl authBusiness;

    @Autowired
    RoleServices roleServices;

    @Autowired
    UserServices userServices;

    @Value("${system.supperAdmin.username}")
    private String superAdminUsername;

    @Value("${system.supperAdmin.password}")
    private String supperAdminPassword;

    @Value("${system.supperAdmin.email}")
    private String supperAdminEmail;

    @Value("${system.supperAdmin.organization}")
    private String superAdminOrganization;

    @PostMapping(path = "")
    public ResponseEntity<BaseResponse> init() {
        UserDto userDto = authBusiness.init(superAdminOrganization, superAdminUsername, supperAdminEmail, supperAdminPassword);
        if (userDto != null) {
            return new ResponseEntity<>(new SignUpResponse(UserErrorCode.SUCCESS.getCode(), UserErrorCode.SUCCESS.getMessage(), userDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new SignUpResponse(UserErrorCode.CAN_NOT_ADD_USER.getCode(), UserErrorCode.CAN_NOT_ADD_USER.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
    }
}
