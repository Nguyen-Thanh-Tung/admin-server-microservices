package com.comit.services.account.server.business;

import com.comit.services.account.client.dto.RoleDto;
import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.client.request.AddUserRequest;
import com.comit.services.account.client.request.LockOrUnlockRequest;
import com.comit.services.account.client.request.UpdateRoleForUserRequest;
import com.comit.services.account.server.constant.Const;
import com.comit.services.account.server.constant.UserErrorCode;
import com.comit.services.account.server.exeption.AccountRestApiException;
import com.comit.services.account.server.middleware.UserVerifyRequestServices;
import com.comit.services.account.server.model.Role;
import com.comit.services.account.server.model.User;
import com.comit.services.account.server.service.RoleServices;
import com.comit.services.account.server.service.UserServices;
import com.comit.services.account.server.util.IDGeneratorUtil;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.metadata.client.dto.MetadataDto;
import com.comit.services.organization.client.dto.OrganizationDto;
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
    private UserVerifyRequestServices verifyRequestServices;
    @Autowired
    private CommonBusiness commonBusiness;

    @Value("${system.supperAdmin.username}")
    private String superAdminUsername;

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userServices.getAllUser();
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
    public UserDto addUser(AddUserRequest request) {
        verifyRequestServices.verifyAddUserRequest(request);
        User currentUser = commonBusiness.getCurrentUser();
        String username = userServices.convertFullnameToUsername(request.getFullname());

        if (userServices.existUserByEmail(request.getEmail())) {
            throw new AccountRestApiException(UserErrorCode.EMAIL_EXISTED);
        }

        Integer organizationId = request.getOrganizationId();
        if (organizationId != null) {
            OrganizationDto organization = userServices.getOrganizationById(organizationId);
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
            LocationDto location = userServices.getLocation(request.getLocationId());
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
        userServices.sendConfirmCreateUserMail(newUser);
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
                OrganizationDto organization = userServices.getOrganizationById(organizationId);
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
                LocationDto location = userServices.getLocation(Integer.parseInt(locationIdStr));
                if (location == null) {
                    throw new AccountRestApiException(UserErrorCode.LOCATION_NOT_EXIST);
                }
                user.setLocationId(location.getId());
            }

            if (currentUser.getId() != user.getId() && !userServices.hasPermissionManageUser(currentUser, user)) {
                throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
            }

            if (file != null) {
                MetadataDto metadata = userServices.saveMetadata(file);
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
            MetadataDto metadata = userServices.saveMetadata(file);
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
            List<User> users = userServices.getAllUser();
            return users.size();
        } else if (currentUser.getParent() != null && Objects.equals(currentUser.getParent().getUsername(), superAdminUsername)) {
            Integer organizationId = commonBusiness.getCurrentUser().getOrganizationId();
            List<User> users = userServices.getUsersByOrganization(organizationId);
            return users.size();
        } else {
            return getAllUser().size();
        }
    }

    @Override
    public List<UserDto> getUsersByOrganizationId(int organizationId) {
        List<User> users = userServices.getUsersByOrganization(organizationId);
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            userDtos.add(convertUserToUserDto(user));
        });
        return userDtos;
    }

    @Override
    public LocationDto getLocationOfCurrentUser() {
        User currentUser = commonBusiness.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        return userServices.getLocation(currentUser.getLocationId());
    }

    @Override
    public OrganizationDto getOrganizationOfCurrentUser() {
        User currentUser = commonBusiness.getCurrentUser();
        return userServices.getOrganizationById(currentUser.getOrganizationId());
    }

    @Override
    public UserDto getCurrentUser() {
        User currentUser = commonBusiness.getCurrentUser();
        return convertUserToUserDto(currentUser);
    }

    @Override
    public List<UserDto> getUsersOfCurrentUser() {
        User currentUser = commonBusiness.getCurrentUser();
        List<User> users = userServices.getUsersByParentId(currentUser.getId());
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(user -> {
            userDtos.add(convertUserToUserDto(user));
        });
        return userDtos;
    }

    @Override
    public List<RoleDto> getRolesOfCurrentUser() {
        User user = commonBusiness.getCurrentUser();
        Set<Role> roles = user.getRoles();
        List<RoleDto> roleDtos = new ArrayList<>();
        roles.forEach(role -> {
            roleDtos.add(convertRoleToRoleDto(role));
        });
        return roleDtos;
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

    public UserDto convertUserToUserDto(User user) {
        if (user == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(user, UserDto.class);
        } catch (Exception e) {
            return null;
        }
    }

//    public UserDto convertUserToUserDto(User user) {
//        if (user == null) return null;
//        ModelMapper modelMapper = new ModelMapper();
//        try {
//            UserDto userDto = modelMapper.map(user, UserDto.class);
//            OrganizationDto organization = userServices.getOrganizationById(user.getOrganizationId());
//            if (organization != null) {
//                userDto.setOrganization(organization);
//            }
//            if (user.getLocationId() != null) {
//                LocationDto location = userServices.getLocation(user.getLocationId());
//                if (location != null) {
//                    userDto.setLocation(location);
//                }
//            }
//            if (user.getAvatarId() != null) {
//                MetadataDto metadata = userServices.getMetadata(user.getAvatarId());
//                if (metadata != null) {
//                    userDto.setAvatar(metadata);
//                }
//            }
//            return userDto;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    @Override
    public int getNumberUserOfLocation(Integer locationId) {
        return userServices.getNumberUserOfLocation(locationId);
    }
}
