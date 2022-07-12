package com.comit.services.account.business;

import com.comit.services.account.client.LocationClient;
import com.comit.services.account.client.MailClient;
import com.comit.services.account.client.MetadataClient;
import com.comit.services.account.client.OrganizationClient;
import com.comit.services.account.client.response.OrganizationResponse;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.constant.Const;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.client.request.MailRequest;
import com.comit.services.account.controller.response.LocationResponse;
import com.comit.services.account.controller.response.MetadataResponse;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.model.entity.Role;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.service.RoleServices;
import com.comit.services.account.service.UserServices;
import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.UpdateRoleForUserRequest;
import com.comit.services.account.middleware.UserVerifyRequestServices;
import com.comit.services.account.model.dto.UserDto;
import com.comit.services.account.util.IDGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    @Autowired
    private OrganizationClient organizationClient;
    @Autowired
    private LocationClient locationClient;
    @Autowired
    private MailClient mailClient;
    @Autowired
    private MetadataClient metadataClient;

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
                try {
                    userDtos.add(UserDto.convertUserToUserDto(user));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return userDtos;
    }

    @Override
    public UserDto getUser(int id) throws IOException {
        User currentUser = commonBusiness.getCurrentUser();
        User user = userServices.getUser(id);
        // Check permission red info user
        if (user == null || (currentUser.getId() != id && !userServices.hasPermissionManageUser(currentUser, user))) {
            return null;
        }

        // Remove information parent user if parent user is super admin
        if (Objects.equals(user.getParent().getUsername(), superAdminUsername)) {
            user.setParent(null);
        }
        return UserDto.convertUserToUserDto(user);
    }

    @Override
    public UserDto addUser(AddUserRequest request) throws IOException {
        verifyRequestServices.verifyAddUserRequest(request);
        User currentUser = commonBusiness.getCurrentUser();
        String username = userServices.convertFullnameToUsername(request.getFullname());

        if (userServices.existUserByEmail(request.getEmail())) {
            throw new AccountRestApiException(UserErrorCode.EMAIL_EXISTED);
        }

        Integer organizationId = request.getOrganizationId();
        if (organizationId != null) {
            OrganizationResponse organizationResponse = organizationClient.getOrganization(organizationId).getBody();
            if (organizationResponse == null || organizationResponse.getOrganization() == null) {
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
            LocationResponse locationResponse = locationClient.getLocation(request.getLocationId()).getBody();
            if (locationResponse == null || locationResponse.getLocation() == null) {
                throw new AccountRestApiException(UserErrorCode.LOCATION_NOT_EXIST);
            }
            user.setLocationId(locationResponse.getLocation().getId());
        }


        // Gen code to verify when change password
        String code = IDGeneratorUtil.gen();
        user.setCode(code);

        if (!userServices.hasPermissionManageUser(currentUser, user)) {
            throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
        }
        User newUser = userServices.saveUser(currentUser, user);
        // Send mail
        MailRequest mailRequest = new MailRequest(newUser.getEmail(), newUser.getFullName(), newUser.getId(), code);
        mailClient.sendMailConfirmCreateUser(mailRequest);
        return UserDto.convertUserToUserDto(newUser);
    }

    @Override
    public UserDto updateRoleUser(int id, UpdateRoleForUserRequest request) throws IOException {
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
    public UserDto updateUser(int id, HttpServletRequest httpServletRequest) throws IOException {
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
                OrganizationResponse organizationResponse = organizationClient.getOrganization(organizationId).getBody();
                if (organizationResponse == null || organizationResponse.getOrganization() == null) {
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
                LocationResponse locationResponse = locationClient.getLocation(Integer.parseInt(locationIdStr)).getBody();
                if (locationResponse == null || locationResponse.getLocation() == null) {
                    throw new AccountRestApiException(UserErrorCode.LOCATION_NOT_EXIST);
                }
                user.setLocationId(locationResponse.getLocation().getId());
            }

            if (currentUser.getId() != user.getId() && !userServices.hasPermissionManageUser(currentUser, user)) {
                throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
            }

            if (file != null) {
                MetadataResponse metadataResponse = metadataClient.saveMetadata(file).getBody();
                if (metadataResponse != null && metadataResponse.getMetadata() != null) {
                    user.setAvatarId(metadataResponse.getMetadata().getId());
                }
            }
            User newUser = userServices.saveUser(user);
            return UserDto.convertUserToUserDto(newUser);
        } else {
            return null;
        }
    }

    @Override
    public UserDto addRoleToUser(int id, Set<String> roles) throws IOException {
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
        return UserDto.convertUserToUserDto(newUser);
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
    public UserDto lockOrUnlockUser(int id, LockOrUnlockRequest request) throws IOException {
        verifyRequestServices.verifyLockOrUnlockRequest(request);
        User currentUser = commonBusiness.getCurrentUser();
        User user = userServices.getUser(id);
        if (user == null) {
            throw new AccountRestApiException(UserErrorCode.USER_NOT_EXIST);
        }

        user.setStatus(request.getIsLock() ? Const.LOCK : Const.ACTIVE);
        User newUser = userServices.saveUser(currentUser, user);
        return UserDto.convertUserToUserDto(newUser);
    }

    @Override
    public UserDto uploadAvatar(int userId, HttpServletRequest httpServletRequest) throws IOException {
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
            MetadataResponse metadataResponse = metadataClient.saveMetadata(file).getBody();
            if (metadataResponse != null && metadataResponse.getMetadata() != null) {
                user.setAvatarId(metadataResponse.getMetadata().getId());
            }
            User newUser = userServices.saveUser(user);
            return UserDto.convertUserToUserDto(newUser);
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
            try {
                userDtos.add(UserDto.convertUserToUserDto(user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return userDtos;
    }
}
