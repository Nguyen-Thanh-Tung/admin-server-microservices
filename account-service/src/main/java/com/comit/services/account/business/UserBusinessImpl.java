package com.comit.services.account.business;

import com.comit.services.account.client.data.LocationDtoClient;
import com.comit.services.account.client.data.MetadataDtoClient;
import com.comit.services.account.client.data.OrganizationDtoClient;
import com.comit.services.account.constant.Const;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.controller.request.UpdateRoleForUserRequest;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.exeption.CommonLogger;
import com.comit.services.account.middleware.UserVerifyRequestServices;
import com.comit.services.account.model.dto.BaseUserDto;
import com.comit.services.account.model.dto.RoleDto;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.model.entity.Role;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.service.KafkaServices;
import com.comit.services.account.service.RoleServices;
import com.comit.services.account.service.UserServices;
import com.comit.services.account.util.IDGeneratorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserBusinessImpl implements UserBusiness {
    @Autowired
    private UserServices userServices;
    @Autowired
    private RoleServices roleServices;
    @Autowired
    private KafkaServices kafkaServices;
    @Autowired
    private UserVerifyRequestServices verifyRequestServices;
    @Autowired
    private CommonBusiness commonBusiness;

    @Value("${system.supperAdmin.username}")
    private String superAdminUsername;

    @Override
    public List<UserDto> getAllUser(String status) {
        List<User> users = userServices.getAllUser(status);
        List<UserDto> userDtos = new ArrayList<>();
        User currentUser = commonBusiness.getCurrentUser();
        // Filter list user
        users.forEach(user -> {
            if (userServices.hasPermissionManageUser(currentUser, user)) {
                userDtos.add(convertUserToUserDto(user));
            }
        });
        return userDtos;
    }

    @Override
    public UserDto getUser(int id) {
        User currentUser = commonBusiness.getCurrentUser();
        User user = userServices.getUser(id);
        // Check permission red info user
        if (user == null || (currentUser.getId() != id && !userServices.hasPermissionManageUser(currentUser, user))) {
            return null;
        }

        // Remove information parent user if parent user is super admin
        if (user.getParent() != null && Objects.equals(user.getParent().getUsername(), superAdminUsername)) {
            user.setParent(null);
        }
        return convertUserToUserDto(user);
    }

    @Override
    public BaseUserDto getUserBase(int id) {
        User user = userServices.getUser(id);
        if (user == null) return null;
        // Remove information parent user if parent user is super admin
        if (user.getParent() != null && Objects.equals(user.getParent().getUsername(), superAdminUsername)) {
            user.setParent(null);
        }
        return convertUserToBaseUserDto(user);
    }

    @Override
    public UserDto addUser(AddUserRequest request) {
        verifyRequestServices.verifyAddUserRequest(request);
        User currentUser = commonBusiness.getCurrentUser();
        String username = userServices.convertFullnameToUsername(request.getFullname());

        if (userServices.existUserByEmail(request.getEmail())) {
            throw new AccountRestApiException(UserErrorCode.EMAIL_EXISTED);
        }

        Integer organizationId = request.getOrganizationId();
        if (organizationId != null) {
            OrganizationDtoClient organization = userServices.getOrganizationById(organizationId);
            if (organization == null) {
                throw new AccountRestApiException(UserErrorCode.ORGANIZATION_NOT_EXIST);
            }
        } else {
            organizationId = commonBusiness.getCurrentUser().getOrganizationId();
        }

        // Create new user's account
        Set<Role> roleSet = new HashSet<>();
        if (request.getRoles().size() > 0) {
            for (String role : request.getRoles()) {
                Role newRole;
                if (roleServices.existsByName(role)) {
                    newRole = roleServices.findRoleByName(role);
                    roleSet.add(newRole);
                }
            }
        }

        User user = new User();
        user.setUsername(username);
        user.setFullName(request.getFullname());
        user.setEmail(request.getEmail());
        user.setRoles(roleSet);
        user.setOrganizationId(organizationId);
        user.setStatus(Const.PENDING);
        user.setParent(commonBusiness.getCurrentUser());

        // Admin create user (in permission, ex: Time Keeping Admin create Time Keeping User)
        if (request.getLocationId() != null) {
            LocationDtoClient location = userServices.getLocation(request.getLocationId());
            user.setLocationId(location.getId());
        }


        // Gen code to verify when change password
        String code = IDGeneratorUtil.gen();
        user.setCode(code);

        if (!userServices.hasPermissionManageUser(currentUser, user)) {
            throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
        }
        User newUser = userServices.saveUser(currentUser, user);
        // Send mail
        try {
            kafkaServices.sendMessage("createUser", "{\"id\":" + newUser.getId() + ",\"fullname\":\"" + newUser.getFullName() + "\",\"username\":\"" + newUser.getUsername() + "\", \"email\": \"" + newUser.getEmail() + "\", \"code\": \"" + newUser.getCode() + "\"}");
        } catch (Exception e) {
            CommonLogger.error("Error kafka");
        }
        return convertUserToUserDto(newUser);
    }

    @Override
    public UserDto updateRoleUser(int id, UpdateRoleForUserRequest request) {
        verifyRequestServices.verifyUpdateRoleForUserRequest(request);
        User currentUser = commonBusiness.getCurrentUser();
        User user = userServices.getUser(id);
        // Check permission update role
        if (user == null || !userServices.hasPermissionManageUser(currentUser, user)) {
            throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        // Delete role Supper admin in roles request
        Set<String> roles = request.getRoles();
        roles.remove(Const.ROLE_SUPER_ADMIN);
        return addRoleToUser(id, roles);
    }

    @Override
    public UserDto updateUser(int id, HttpServletRequest httpServletRequest) {
        User user = userServices.getUser(id);
        if (user == null) {
            throw new AccountRestApiException(UserErrorCode.USER_NOT_EXIST);
        }
        String contentType = httpServletRequest.getContentType();
        if (contentType == null || !contentType.contains("multipart/form-data")) {
            throw new AccountRestApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        }
        if (httpServletRequest instanceof MultipartHttpServletRequest multipartHttpServletRequest) {
            MultipartFile file = multipartHttpServletRequest.getFile("file");
            String organizationIdStr = httpServletRequest.getParameter("organization_id");
            String roleStrs = httpServletRequest.getParameter("roles");
            String fullName = httpServletRequest.getParameter("fullname");
            String email = httpServletRequest.getParameter("email");
            String locationIdStr = httpServletRequest.getParameter("location_id");
            verifyRequestServices.verifyUpdateUserRequest(file, organizationIdStr, locationIdStr, roleStrs, fullName, email);

            if (!Objects.equals(email, user.getEmail()) && userServices.existUserByEmail(email)) {
                throw new AccountRestApiException(UserErrorCode.EMAIL_EXISTED);
            }

            User currentUser = commonBusiness.getCurrentUser();
            Integer organizationId;
            if (organizationIdStr != null) {
                organizationId = Integer.parseInt(organizationIdStr);
                OrganizationDtoClient organization = userServices.getOrganizationById(organizationId);
                if (organization == null) {
                    throw new AccountRestApiException(UserErrorCode.ORGANIZATION_NOT_EXIST);
                }
            } else {
                organizationId = currentUser.getOrganizationId();
            }

            // Create new user's account
            Set<Role> roleSet = new HashSet<>();
            List<String> roles = List.of(roleStrs.trim().substring(1, roleStrs.length() - 1).split(","));

            for (String role : roles) {
                Role newRole;
                role = role.substring(1, role.length() - 1); // ""ABC""
                if (roleServices.existsByName(role)) {
                    newRole = roleServices.findRoleByName(role);
                    roleSet.add(newRole);
                }
            }

            user.setFullName(fullName);
            user.setEmail(email);
            user.setRoles(roleSet);
            user.setOrganizationId(organizationId);

            if (locationIdStr != null && !locationIdStr.trim().isEmpty()) {
                LocationDtoClient location = userServices.getLocation(Integer.parseInt(locationIdStr));
                if (location == null) {
                    throw new AccountRestApiException(UserErrorCode.LOCATION_NOT_EXIST);
                }
                user.setLocationId(location.getId());
            }

            if (currentUser.getId() != user.getId() && !userServices.hasPermissionManageUser(currentUser, user)) {
                throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
            }

            if (file != null) {
                MetadataDtoClient metadata = userServices.saveMetadata(file);
                if (metadata != null) {
                    user.setAvatarId(metadata.getId());
                }
            }
            User newUser = userServices.saveUser(user);
            return convertUserToUserDto(newUser);
        } else {
            return null;
        }
    }

    @Override
    public UserDto addRoleToUser(int id, Set<String> roles) {
        User currentUser = commonBusiness.getCurrentUser();
        User user = userServices.getUser(id);
        if (user != null && roles.size() > 0) {
            Set<Role> roleSet = new HashSet<>();
            for (String role : roles) {
                Role newRole;
                if (roleServices.existsByName(role)) {
                    newRole = roleServices.findRoleByName(role);
                    roleSet.add(newRole);
                }
            }
            user.setRoles(roleSet);
        }
        User newUser = userServices.saveUser(currentUser, user);
        return convertUserToUserDto(newUser);
    }

    @Override
    public boolean deleteUser(int id) {
        User currentUser = commonBusiness.getCurrentUser();
        User user = userServices.getUser(id);
        // Check permission delete user
        if (user == null || !userServices.hasPermissionManageUser(currentUser, user)) {
            return false;
        }

        user.setStatus(Const.DELETED);
        userServices.saveUser(user);
        return true;
    }

    @Override
    public UserDto lockOrUnlockUser(int id, LockOrUnlockRequest request) {
        verifyRequestServices.verifyLockOrUnlockRequest(request);
        User currentUser = commonBusiness.getCurrentUser();
        User user = userServices.getUser(id);
        if (user == null) {
            throw new AccountRestApiException(UserErrorCode.USER_NOT_EXIST);
        }

        user.setStatus(request.getIsLock() ? Const.LOCK : Const.ACTIVE);
        User newUser = userServices.saveUser(currentUser, user);
        return convertUserToUserDto(newUser);
    }

    @Override
    public UserDto uploadAvatar(int userId, HttpServletRequest httpServletRequest) {
        User user = userServices.getUser(userId);
        if (user == null) {
            throw new AccountRestApiException(UserErrorCode.USER_NOT_EXIST);
        }
        String contentType = httpServletRequest.getContentType();
        if (contentType == null || !contentType.contains("multipart/form-data")) {
            throw new AccountRestApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        }
        if (httpServletRequest instanceof MultipartHttpServletRequest multipartHttpServletRequest) {
            MultipartFile file = multipartHttpServletRequest.getFile("file");

            verifyRequestServices.verifyUploadAvatar(file);
            MetadataDtoClient metadata = userServices.saveMetadata(file);
            if (metadata != null) {
                user.setAvatarId(metadata.getId());
            }
            User newUser = userServices.saveUser(user);
            return convertUserToUserDto(newUser);
        } else {
            return null;
        }
    }

    @Override
    public int getNumberAccount() {
        User currentUser = commonBusiness.getCurrentUser();
        if (userServices.hasRole(currentUser, Const.ROLE_SUPER_ADMIN)) {
            List<User> users = userServices.getAllUser(Const.ACTIVE);
            return users.size();
        } else if (currentUser.getParent() != null && Objects.equals(currentUser.getParent().getUsername(), superAdminUsername)) {
            Integer organizationId = commonBusiness.getCurrentUser().getOrganizationId();
            return userServices.getNumberUserOfOrganization(organizationId);
        } else {
            return getAllUser(Const.ACTIVE).size();
        }
    }

    @Override
    public int getNumberUserOfOrganization(int organizationId) {
        return userServices.getNumberUserOfOrganization(organizationId);
    }

    @Override
    public int getNumberAllUserOfOrganization(int organizationId) {
        return userServices.getNumberAllUserOfOrganization(organizationId);
    }

    @Override
    public BaseUserDto getCurrentUser() {
        User currentUser = commonBusiness.getCurrentUser();
        return convertUserToBaseUserDto(currentUser);
    }

    @Override
    public List<BaseUserDto> getUsersOfCurrentUser() {
        User currentUser = commonBusiness.getCurrentUser();
        List<User> users = userServices.getUsersByParentId(currentUser.getId());
        List<BaseUserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            userDtos.add(convertUserToBaseUserDto(user));
        });
        return userDtos;
    }

    @Override
    public List<RoleDto> getRolesOfCurrentUser() {
        User user = commonBusiness.getCurrentUser();
        Set<Role> roles = user.getRoles();
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach(role -> {
            roleDtos.add(RoleDto.convertRoleToRoleDto(role));
        });
        return roleDtos;
    }

    public BaseUserDto convertUserToBaseUserDto(User user) {
        if (user == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(user, BaseUserDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public UserDto convertUserToUserDto(User user) {
        if (user == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        try {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            OrganizationDtoClient organization = userServices.getOrganizationById(user.getOrganizationId());
            if (organization != null) {
                userDto.setOrganization(organization);
            }
            if (user.getLocationId() != null) {
                LocationDtoClient location = userServices.getLocation(user.getLocationId());
                if (location != null) {
                    userDto.setLocation(location);
                }
            }
            if (user.getAvatarId() != null) {
                MetadataDtoClient metadata = userServices.getMetadata(user.getAvatarId());
                if (metadata != null) {
                    userDto.setAvatar(metadata);
                }
            }
            return userDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getNumberUserOfLocation(Integer locationId) {
        return userServices.getNumberUserOfLocation(locationId);
    }

    @Override
    public int getNumberUserOfRoles(String roleIdStrs) {
        User currentUser = commonBusiness.getCurrentUser();
        String[] tmp = roleIdStrs.split(",");
        List<Integer> roleIds = new ArrayList<>();
        for (String s : tmp) {
            roleIds.add(Integer.parseInt(s));
        }
        if (Objects.equals(currentUser.getUsername(), superAdminUsername)) {
            return userServices.getNumberUserOfRoles(roleIds);
        }
        return userServices.getNumberUserOfRoles(currentUser.getOrganizationId(), roleIds);
    }

    @Override
    public int getNumberOrganizationOfRoles(String roleIdStrs) {
        User currentUser = commonBusiness.getCurrentUser();
        String[] tmp = roleIdStrs.split(",");
        List<Integer> roleIds = new ArrayList<>();
        for (String s : tmp) {
            roleIds.add(Integer.parseInt(s));
        }
        if (Objects.equals(currentUser.getUsername(), superAdminUsername)) {
            return userServices.getNumberOrganizationOfRoles(roleIds);
        }
        return 0;
    }

    @Override
    public boolean resendCode(Integer userId) {
        User user = userServices.getUser(userId);
        if (user != null && user.getCode() != null) {
            // Send mail
            try {
                kafkaServices.sendMessage("createUser", "{\"id\": " + user.getId() + ", \"fullname\": \"" + user.getFullName() + "\",\"username\":\"" + user.getUsername() + "\", \"email\": \"" + user.getEmail() + "\", \"code\": \"" + user.getCode() + "\"}");
                return true;
            } catch (Exception e) {
                CommonLogger.error("Error kafka: " + e.getMessage());
                return false;
            }
        }
        return false;
    }
}
