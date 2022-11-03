package com.comit.services.account.controller;

import com.comit.services.account.business.AuthBusinessImpl;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.response.BaseResponse;
import com.comit.services.account.controller.response.SignUpResponse;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.service.RoleServices;
import com.comit.services.account.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
            return new ResponseEntity<>(new SignUpResponse(UserErrorCode.SUCCESS, userDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new SignUpResponse(UserErrorCode.CAN_NOT_ADD_USER, null), HttpStatus.BAD_REQUEST);
        }
    }
}
