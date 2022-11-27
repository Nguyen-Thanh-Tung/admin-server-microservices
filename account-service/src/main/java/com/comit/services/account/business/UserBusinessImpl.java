package com.comit.services.account.business;

import com.comit.services.account.client.data.LocationDtoClient;
import com.comit.services.account.client.data.MetadataDtoClient;
import com.comit.services.account.client.data.OrganizationDtoClient;
import com.comit.services.account.constant.AuthErrorCode;
import com.comit.services.account.constant.Const;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.exeption.AuthException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<User> getAllUser(int size, int page, String search, String status) {
        Pageable paging = PageRequest.of(page, size);
        User currentUser = commonBusiness.getCurrentUser();
        if (userServices.isCadres(currentUser)) {
            CommonLogger.error(UserErrorCode.PERMISSION_DENIED.getMessage() + ": getAllUser(int size, ...) " +
                    "- currentUser is cadre, currentUserId: " + currentUser.getId());
            throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
        }
        String currentRoleSubUser = commonBusiness.findCadreRoleFromModule();
        return userServices.getAllUser(status, search, paging, currentUser, currentRoleSubUser);
    }

    @Override
    public List<UserDto> getAllUser(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        // Filter list user
        users.forEach(user -> {
            UserDto userDto = convertUserToUserDtoWithModule(user, null);
            if (userDto != null) {
                userDtos.add(userDto);
            }
        });
        return userDtos;
    }

    @Override
    public UserDto getUser(int id) {
        User currentUser = commonBusiness.getCurrentUser();
        User user = userServices.getUser(id);
        if (user == null) {
            throw new AccountRestApiException(UserErrorCode.USER_NOT_EXIST);
        }

        // Check permission read info user
        if (currentUser.getId() != id && !userServices.hasPermissionManageUser(currentUser, user)) {
            CommonLogger.error(UserErrorCode.PERMISSION_DENIED.getMessage() + ": getUser() " +
                    "- hasPermissionManageUser() return false, currentUserId: " + currentUser.getId() + " and userId: " + id);
            throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
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
        if (user == null) throw new AccountRestApiException(UserErrorCode.USER_NOT_EXIST);
        // Remove information parent user if parent user is super admin
        if (user.getParent() != null && Objects.equals(user.getParent().getUsername(), superAdminUsername)) {
            user.setParent(null);
        }
        return convertUserToBaseUserDto(user);
    }

    @Override
    public UserDto addUser(AddUserRequest request) {
        verifyRequestServices.verifyAddUserRequest(request);
        Integer organizationId = request.getOrganizationId();
        Integer locationId = request.getLocationId();
        User currentUser = commonBusiness.getCurrentUser();
        String username = userServices.convertFullnameToUsername(request.getFullname());
        /* check email exist */
        if (userServices.existUserByEmail(request.getEmail())) {
            throw new AccountRestApiException(UserErrorCode.EMAIL_EXISTED);
        }
        /* check and build roles exist */
        Set<Role> roleSet = userServices.checkRoleAddUser(request.getRoles(), currentUser);

        User user = new User();
        int newOrganizationId = userServices.checkPermissionAddOrganizationId(currentUser, organizationId);
        int newLocationId = userServices.checkPermissionAddLocationId(currentUser, locationId);
        if (newLocationId != 0) {
            user.setLocationId(newLocationId);
        }
        user.setUsername(username);
        user.setFullName(request.getFullname());
        user.setOrganizationId(newOrganizationId);
        user.setEmail(request.getEmail());
        user.setRoles(roleSet);
        user.setStatus(Const.PENDING);
        user.setParent(commonBusiness.getCurrentUser());
        String code = IDGeneratorUtil.gen(); // Gen code to verify when change password
        user.setCode(code);
        /* check match location_type and role */
        if (userServices.isAdmin(currentUser)) {
            LocationDtoClient location = userServices.getLocation(locationId);
            if (!userServices.checkLocationTypeAndRole(user, location.getType())) {
                CommonLogger.error(UserErrorCode.PERMISSION_DENIED.getMessage() + ": addUser() - location_type: "
                        + location.getType() + " not match role: " + user.toString(user.getRoles()));
                throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
            }
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
    public UserDto updateUser(int id, HttpServletRequest httpServletRequest) {
        //Get user by id
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
            // validate input
            verifyRequestServices.verifyUpdateUserRequest(file, roleStrs, fullName, email, user, locationIdStr);
            if (!Objects.equals(email, user.getEmail()) && userServices.existUserByEmail(email)) { // check mail exist
                throw new AccountRestApiException(UserErrorCode.EMAIL_EXISTED);
            }
            user.setEmail(email);
            User currentUser = commonBusiness.getCurrentUser();

            if (currentUser.getId() != user.getId()) {
                if (!userServices.hasPermissionManageUser(currentUser, user)) {// check permission current user
                    CommonLogger.error(UserErrorCode.PERMISSION_DENIED.getMessage() + ": updateUser() " +
                            "- hasPermissionManageUser() return false, currentUserId: " + currentUser.getId() + " and userId: " + id);
                    throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
                }

                /* set roles */
                Set<Role> roles = convertRoleFromStringToArray(user, roleStrs.trim().replaceAll(", ", ","), locationIdStr);
                user.setRoles(roles);
                List<User> subUsers = userServices.getAllUserByParentId(user.getId());
                for (User subUser : subUsers) {
                    for (Role role : subUser.getRoles()) {
                        if (!checkRoleAddAndUpdateUser(user, role.getName())) {
                            CommonLogger.error(UserErrorCode.CAN_NOT_UPDATE_USER.getMessage() + ": updateUser - new role and role of subUser wrong, userId: " + id);
                            throw new AccountRestApiException(UserErrorCode.CAN_NOT_UPDATE_USER);
                        }
                    }
                }
                /* only SuperAdmin has role update organization */
                if (userServices.isSuperAdmin(currentUser)) {
                    user.setOrganizationId(userServices.checkPermissionUpdateAndGetOrganizationId(user, organizationIdStr));
                }
                /* update locationId for cadres (locationId require non null, currentUser must be admin) */
                int locationId = userServices.checkPermissionUpdateAndGetLocationId(currentUser, locationIdStr);
                if (locationId != 0) {
                    user.setLocationId(locationId);
                }
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
            throw new AccountRestApiException(UserErrorCode.NOT_IS_MULTIPART);
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
        if (user == null) {
            throw new AccountRestApiException(UserErrorCode.USER_NOT_EXIST);
        }
        if (!userServices.hasPermissionManageUser(currentUser, user)) {
            CommonLogger.error(UserErrorCode.PERMISSION_DENIED.getMessage() + ": deleteUser() " +
                    "- hasPermissionManageUser() return false, currentUserId: " + currentUser.getId() + " and userId: " + id);
            throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
        }
        if (userServices.isSuperAdminOrganization(user) || userServices.isAdmin(user)) {
            // Check sub users exist
            List<User> subUsers = userServices.getAllUserByParentId(id);
            if (subUsers.size() > 0) {
                throw new AccountRestApiException(UserErrorCode.CAN_NOT_DELETE_USER);
            }
        }
        if (userServices.isCadres(user)) {
            int numberEmployeeOfLocation = userServices.getNumberEmployeeOfLocation(user.getLocationId());
            int numberUserOfLocation = userServices.getNumberUserOfLocation(user.getLocationId());
            if (numberUserOfLocation == 1 && numberEmployeeOfLocation > 0) {
                throw new AccountRestApiException(UserErrorCode.CAN_NOT_DELETE_USER);
            }
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
            throw new AccountRestApiException(UserErrorCode.NOT_IS_MULTIPART);
        }
    }

    @Override
    public int getNumberAccount() {
        User currentUser = commonBusiness.getCurrentUser();
        if (userServices.hasRole(currentUser, Const.ROLE_SUPER_ADMIN)) {
            List<User> users = userServices.getAllUser(Const.ACTIVE);
            return users.size();
        } else if (userServices.isSuperAdminOrganization(currentUser)) {
            Integer organizationId = commonBusiness.getCurrentUser().getOrganizationId();
            return userServices.getNumberUserOfOrganization(organizationId);
        } else {
            return userServices.getAllUserByParentId(Const.ACTIVE, currentUser.getId()).size() + 1;
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
            CommonLogger.error(e.getMessage(), e);
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
            CommonLogger.error(e.getMessage(), e);
            return null;
        }
    }

    public UserDto convertUserToUserDtoWithModule(User user, String keyModule) {
        if (user == null) return null;
        UserDto userDto = convertUserToUserDto(user);
        if (keyModule == null || (user.getRoles() != null && userDto.toString(user.getRoles()).contains(keyModule))) {
            return userDto;
        }
        return null;
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
        User currentUser = commonBusiness.getCurrentUser();
        User user = userServices.getUser(userId);
        if (!userServices.hasPermissionManageUser(currentUser, user)) {
            throw new AuthException(AuthErrorCode.PERMISSION_DENIED);
        }
        if (user != null && user.getCode() != null) {
            if (user.getStatus().equals(Const.ACTIVE)) {
                throw new AuthException(AuthErrorCode.ACCOUNT_ACTIVE);
            }
            // Send mail
            try {
                kafkaServices.sendMessage("createUser", "" +
                        "{" +
                        "\"id\": " + user.getId() + ", " +
                        "\"fullname\": \"" + user.getFullName() + "\", " +
                        "\"username\": \"" + user.getUsername() + "\", " +
                        "\"email\": \"" + user.getEmail() + "\", " +
                        "\"code\": \"" + user.getCode() + "\", " +
                        "\"is_resend\" : true" +
                        "}"
                );
                return true;
            } catch (Exception e) {
                CommonLogger.error("Error kafka: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean checkRole(String roleNeedCheck) {
        User currentUser = commonBusiness.getCurrentUser();
        return (userServices.isSuperAdmin(currentUser) && Const.ROLE_SUPER_ADMIN.equals(roleNeedCheck))
                || (userServices.isSuperAdminOrganization(currentUser) && Const.ROLE_SUPER_ADMIN_ORGANIZATION.equals(roleNeedCheck))
                || (userServices.isTimeKeepingAdmin(currentUser) && Const.ROLE_TIME_KEEPING_ADMIN.equals(roleNeedCheck))
                || (userServices.isTimeKeepingUser(currentUser) && Const.ROLE_TIME_KEEPING_USER.equals(roleNeedCheck))
                || (userServices.isAreaRestrictionAdmin(currentUser) && Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN.equals(roleNeedCheck))
                || (userServices.isAreaRestrictionUser(currentUser) && Const.ROLE_AREA_RESTRICTION_CONTROL_USER.equals(roleNeedCheck))
                || (userServices.isBehaviorAdmin(currentUser) && Const.ROLE_BEHAVIOR_CONTROL_ADMIN.equals(roleNeedCheck))
                || (userServices.isBehaviorUser(currentUser) && Const.ROLE_BEHAVIOR_CONTROL_USER.equals(roleNeedCheck));
    }

    @Override
    public boolean checkRoleAddAndUpdateUser(User user, String roleNeedCheck) {
        User currentUser = commonBusiness.getCurrentUser();
        if (user == null) {
            user = currentUser;
        }
        return (userServices.isSuperAdmin(user) && (Const.ROLE_TIME_KEEPING_ADMIN.equals(roleNeedCheck)
                || Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN.equals(roleNeedCheck)
                || Const.ROLE_BEHAVIOR_CONTROL_ADMIN.equals(roleNeedCheck)))
                || (userServices.isSuperAdminOrgTimeKeeping(user) && Const.ROLE_TIME_KEEPING_ADMIN.equals(roleNeedCheck))
                || (userServices.isSuperAdminOrgAreaRestriction(user) && Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN.equals(roleNeedCheck))
                || (userServices.isSuperAdminOrgBehavior(user) && Const.ROLE_BEHAVIOR_CONTROL_ADMIN.equals(roleNeedCheck))
                || (userServices.isBehaviorAdmin(user) && Const.ROLE_BEHAVIOR_CONTROL_USER.equals(roleNeedCheck))
                || (userServices.isAreaRestrictionAdmin(user) && Const.ROLE_AREA_RESTRICTION_CONTROL_USER.equals(roleNeedCheck))
                || (userServices.isTimeKeepingAdmin(user) && Const.ROLE_TIME_KEEPING_USER.equals(roleNeedCheck));
    }

    @Override
    public Set<Role> convertRoleFromStringToArray(User user, String roleStr, String locationIdStr) {
        User currentUser = commonBusiness.getCurrentUser();
        Set<Role> roleSet = new HashSet<>();
        List<String> roles = List.of(roleStr.substring(1, roleStr.length() - 1).split(","));
        if (userServices.isAdmin(currentUser) && !userServices.isRoleOfUser(user, roles)) { // unable update role for cadre
            CommonLogger.error(UserErrorCode.PERMISSION_DENIED.getMessage() + ": convertRoleFromStringToArray() - "
                    + " cant update role for cadre, userId: " + user.getId());
            throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
        }
        if (roles.size() > 0) {
            for (String role : roles) {
                Role newRole;
                role = role.trim().substring(1, role.length() - 1); // ""ABC""
                if (!checkRoleAddAndUpdateUser(null, role)) {
                    CommonLogger.error(UserErrorCode.PERMISSION_DENIED.getMessage() + ": checkRoleAddAndUpdateUser() - " +
                            "currentUserId: " + currentUser.getId() + " and role: " + role);
                    throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
                }
                newRole = roleServices.findRoleByName(role);
                roleSet.add(newRole);
            }
        }

        return roleSet;
    }
}
