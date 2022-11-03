package com.comit.services.account.business;

import com.comit.services.account.client.data.OrganizationDtoClient;
import com.comit.services.account.constant.AuthErrorCode;
import com.comit.services.account.constant.Const;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.request.ChangePasswordRequest;
import com.comit.services.account.controller.request.ForgetPasswordRequest;
import com.comit.services.account.controller.request.LoginRequest;
import com.comit.services.account.controller.request.SignUpRequest;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.exeption.AuthException;
import com.comit.services.account.exeption.CommonLogger;
import com.comit.services.account.jwt.JwtProvider;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.model.entity.Organization;
import com.comit.services.account.model.entity.Role;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.model.entity.UserDetailImpl;
import com.comit.services.account.service.KafkaServices;
import com.comit.services.account.service.RoleServices;
import com.comit.services.account.service.UserServices;
import com.comit.services.account.service.VerifyAdminRequestServices;
import com.comit.services.account.util.IDGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class AuthBusinessImpl implements AuthBusiness {
    @Autowired
    UserBusiness userBusiness;

    @Autowired
    UserServices userServices;

    @Autowired
    RoleServices roleServices;

    @Autowired
    KafkaServices kafkaServices;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    VerifyAdminRequestServices verifyRequestServices;

    @Autowired
    JwtProvider tokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Override
    public String getTokenLogin(LoginRequest request) {
        verifyRequestServices.verifyLoginRequest(request);
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return tokenProvider.generateJwtToken(authentication);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public UserDto login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userServices.getUser(request.getUsername());
        if (Objects.equals(user.getStatus(), Const.LOCK)) {
            throw new AuthException(String.valueOf(AuthErrorCode.USER_IS_BLOCK));
        }
        if (Objects.equals(user.getStatus(), Const.PENDING)) {
            user.setStatus(Const.ACTIVE);
            User newUser = userServices.saveUser(user);
            return userBusiness.convertUserToUserDto(newUser);
        } else {
            return userBusiness.convertUserToUserDto(user);
        }
    }

    @Override
    public UserDto register(SignUpRequest signUpRequest) {
        verifyRequestServices.verifyRegisterRequest(signUpRequest);
        String currentUsername = tokenProvider.getUserNameFromJwtToken(httpServletRequest.getHeader("token"));
        User currentUser = userServices.getUser(currentUsername);
        String username = userServices.convertFullnameToUsername(signUpRequest.getFullname());
        if (!username.equals("admin")) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!(principal instanceof UserDetailImpl)) {
                throw new AuthException(AuthErrorCode.PERMISSION_DENIED);
            }
        }

        if (userServices.existUserByEmail(signUpRequest.getEmail())) {
            throw new AccountRestApiException(UserErrorCode.EMAIL_EXISTED);
        }

        OrganizationDtoClient organization = userServices.getOrganizationById(signUpRequest.getOrganizationId());

        // Create new user's account
        Set<Role> roleSet = new HashSet<>();
        if (signUpRequest.getRoles().size() > 0) {
            for (String role : signUpRequest.getRoles()) {
                Role newRole;
                if (roleServices.existsByName(role)) {
                    newRole = roleServices.findRoleByName(role);
                    roleSet.add(newRole);
                }
            }
        }

        User user = new User();
        user.setUsername(username);
        user.setFullName(signUpRequest.getFullname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRoles(roleSet);
        user.setOrganizationId(organization.getId());
        user.setStatus(Const.PENDING);

        if (!userServices.hasPermissionManageUser(currentUser, user)) {
            throw new AuthException(AuthErrorCode.PERMISSION_DENIED);
        }
        User newUser = userServices.saveUser(currentUser, user);

        return userBusiness.convertUserToUserDto(newUser);
    }

    @Override
    public UserDto init(String organizationName, String username, String email, String password) {
        // Add all Role to system
        Const.ROLES.forEach(role -> {
            if (!roleServices.existsByName(role)) {
                roleServices.addRole(new Role(role));
            }
        });

        // Add feature
        roleServices.addFeature(Const.TIME_KEEPING_MODULE);
        roleServices.addFeature(Const.AREA_RESTRICTION_CONTROL_MODULE);
        roleServices.addFeature(Const.BEHAVIOR_CONTROL_MODULE);

        // Add supper admin
        Set<String> roles = new HashSet<>();
        roles.add(Const.ROLE_SUPER_ADMIN);

        // Create organization
        OrganizationDtoClient organizationDtoClient = userServices.getOrganizationByName(organizationName);

        if (organizationDtoClient == null) {
            Organization organization = new Organization();
            organization.setName(organizationName);
            organizationDtoClient = userServices.addOrganization(organization);
        }


        User user = userServices.getUser(username);
        if (user == null) {
            // Create new user's account
            Set<Role> roleSet = new HashSet<>();
            for (String role : roles) {
                Role newRole;
                if (roleServices.existsByName(role)) {
                    newRole = roleServices.findRoleByName(role);
                    roleSet.add(newRole);
                }
            }

            user = new User();
            user.setUsername(username);
            user.setFullName(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(roleSet);
            user.setOrganizationId(organizationDtoClient.getId());
            user.setStatus(Const.PENDING);

            User newUser = userServices.saveUser(user);
            return userBusiness.convertUserToUserDto(newUser);
        }
        return userBusiness.convertUserToUserDto(user);
    }

    @Override
    public User changePassword(ChangePasswordRequest request) {
        verifyRequestServices.verifyChangePasswordRequest(request);
        User user = userServices.getUser(request.getUserId());
        if (user == null) {
            throw new AccountRestApiException(UserErrorCode.USER_NOT_EXIST);
        }
        if (request.getOldPassword() != null && !request.getOldPassword().isEmpty()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), request.getOldPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            if (!Objects.equals(user.getCode(), request.getCode())) {
                throw new AuthException(AuthErrorCode.CODE_IS_NOT_VERIFIED);
            }
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setCode(null);
        return userServices.saveUser(user);
    }

    @Override
    public void forgetPassword(ForgetPasswordRequest request) {
        verifyRequestServices.verifyForgetPasswordRequest(request);
        User user = userServices.getUserByEmail(request.getEmail());
        if (user == null) {
            throw new AccountRestApiException(UserErrorCode.USER_NOT_EXIST);
        }

        String code = IDGeneratorUtil.gen();
        user.setCode(code);
        User newUser = userServices.saveUser(user);
        // Send mail
        try {
            kafkaServices.sendMessage("forgetPassword", "{\"id\": " + newUser.getId() + ", \"fullname\": \"" + newUser.getFullName() + "\", \"email\": \"" + newUser.getEmail() + "\", \"code\": \"" + newUser.getCode() + "\"}");
        } catch (Exception e) {
            CommonLogger.error("Error kafka");
        }
    }
}
