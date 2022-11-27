package com.comit.services.account.service;

import com.comit.services.account.client.EmployeeClient;
import com.comit.services.account.client.MetadataClient;
import com.comit.services.account.client.OrganizationClient;
import com.comit.services.account.client.data.LocationDtoClient;
import com.comit.services.account.client.data.MetadataDtoClient;
import com.comit.services.account.client.data.OrganizationDtoClient;
import com.comit.services.account.client.request.OrganizationRequestClient;
import com.comit.services.account.client.response.CountResponseClient;
import com.comit.services.account.client.response.LocationResponseClient;
import com.comit.services.account.client.response.MetadataResponseClient;
import com.comit.services.account.client.response.OrganizationResponseClient;
import com.comit.services.account.constant.Const;
import com.comit.services.account.constant.RoleErrorCode;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.exeption.CommonLogger;
import com.comit.services.account.jwt.JwtProvider;
import com.comit.services.account.model.entity.Organization;
import com.comit.services.account.model.entity.Role;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.repository.UserRepository;
import com.comit.services.account.util.RequestHelper;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.Normalizer;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Stream.generate;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestHelper requestHelper;
    @Autowired
    OrganizationClient organizationClient;
    @Autowired
    com.comit.services.account.client.LocationClient locationClient;
    @Autowired
    MetadataClient metadataClient;
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    EmployeeClient employeeClient;
    @Value("${system.supperAdmin.username}")
    private String superAdminUsername;
    @Value("${app.internalToken}")
    private String internalToken;
    @Autowired
    private RoleServices roleServices;
    @Autowired
    JwtProvider tokenProvider;

    public boolean existUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existUserByEmail(String email) {
        return userRepository.existsByEmailAndStatusNotIn(email, List.of(Const.DELETED));
    }

    public List<User> getAllUser(String status) {
        if (status == null || status.trim().isEmpty()) {
            return userRepository.findAll();
        }
        return userRepository.findAllByStatusOrderByStatusAscIdDesc(status);
    }

    @Override
    public Page<User> getAllUser(String status, String search, Pageable pageable, User currentUser, String currentRoleSubUser) {
        int id = currentUser.getId();
        if (search == null || search.trim().isEmpty()) {
            if (status == null || status.trim().isEmpty()) {
                if (isSuperAdmin(currentUser)) {
                    return userRepository.findAllByParentIdOrderByIdDesc(id, pageable);
                } else if (isSuperAdminOrganization(currentUser)) {
                    return userRepository.findAllByOrganizationIdAndParentIdNotAndRoleNameOrderByIdDesc(currentUser.getOrganizationId(),
                            currentUser.getParent().getId(), buildListRoleForQuery(currentUser.getRoles()), pageable);
                } else {
                    return userRepository.findAllByOrganizationIdAndRoleNameOrderByIdDesc(
                            currentUser.getOrganizationId(), currentRoleSubUser, pageable);
                }
            } else {
                if (isSuperAdmin(currentUser)) {
                    return userRepository.findAllByParentIdAndStatusOrderByIdDesc(id, status, pageable);
                } else if (isSuperAdminOrganization(currentUser)) {
                    return userRepository.findAllByOrganizationIdAndParentIdNotAndRoleNameAndStatusOrderByIdDesc(currentUser.getOrganizationId(),
                            currentUser.getParent().getId(), buildListRoleForQuery(currentUser.getRoles()), status, pageable);
                } else {
                    return userRepository.findAllByOrganizationIdAndRoleNameAndStatusOrderByIdDesc
                            (currentUser.getOrganizationId(), currentRoleSubUser, status, pageable);
                }
            }
        } else {
            if (status == null || status.trim().isEmpty()) {
                if (isSuperAdmin(currentUser)) {
                    return userRepository.findAllByParentIdAndSearchOrderByIdDesc(id, search, pageable);
                } else if (isSuperAdminOrganization(currentUser)) {
                    return userRepository.findAllByOrganizationIdAndParentIdNotAndRoleNameAndSearchOrderByIdDesc(currentUser.getOrganizationId(),
                            currentUser.getParent().getId(), buildListRoleForQuery(currentUser.getRoles()), search, pageable);
                } else {
                    return userRepository.findAllByOrganizationIdAndRoleNameAndSearchOrderByIdDesc(
                            currentUser.getOrganizationId(), currentRoleSubUser, search, pageable);
                }
            } else {
                if (isSuperAdmin(currentUser)) {
                    return userRepository.findAllByParentIdAndStatusAndSearchOrderByIdDesc(id, status, search, pageable);
                } else if (isSuperAdminOrganization(currentUser)) {
                    return userRepository.findAllByOrganizationIdAndParentIdNotAndRoleNameAndStatusAndSearchOrderByIdDesc(currentUser.getOrganizationId(),
                            currentUser.getParent().getId(), buildListRoleForQuery(currentUser.getRoles()), status, search, pageable);
                } else {
                    return userRepository.findAllByOrganizationIdAndRoleNameAndStatusAndSearchOrderByIdDesc
                            (currentUser.getOrganizationId(), currentRoleSubUser, status, search, pageable);
                }
            }
        }
    }

    public List<User> getAllUserByParentId(String status, int id) {
        if (status == null || status.trim().isEmpty()) {
            return userRepository.findAllByParentIdOrderByStatusAscIdDesc(id);
        }
        return userRepository.findAllByStatusAndParentIdOrderByStatusAscIdDesc(status, id);
    }

    public User getUser(int id) {
        return userRepository.findByIdAndStatusNotIn(id, List.of(Const.DELETED));
    }

    @Override
    public List<User> getAllUserByParentId(int id) {
        return userRepository.findAllByParentIdAndStatusNotIn(id, List.of(Const.DELETED));
    }

    public User getUser(String username) {
        return userRepository.findByUsernameAndStatusNotIn(username, List.of(Const.DELETED));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndStatusNotIn(email, List.of(Const.DELETED));
    }

    public User saveUser(User currentUser, User user) {
        if (user != null) {
            if (!hasPermissionManageUser(currentUser, user)) {
                CommonLogger.error(UserErrorCode.PERMISSION_DENIED.getMessage() + ": saveUser() - currentUserId: " + currentUser.getId() + ", userId: " + user.getId());
                throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
            }
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteUser(String username) {
        try {
            userRepository.deleteByUsername(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasRole(int id, String roleName) {
        User user = getUser(id);
        for (Role role :
                user.getRoles()) {
            if (Objects.equals(role.getName(), roleName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasRole(User user, String roleName) {
        for (Role role : user.getRoles()) {
            if (Objects.equals(role.getName(), roleName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check permission add user
     * Only super admin can create admin
     * Only admin can create user
     *
     * @param user User need check
     * @return boolean
     */
    public boolean hasPermissionManageUser(User currentUser, User user) {
        boolean userAndCurrentUserBelongOrganization = belongOrganization(currentUser, user.getOrganizationId());
        // Prevent access to super admin
        if (hasRole(user, Const.ROLE_SUPER_ADMIN)) {
            return false;
        }
        AtomicInteger number = new AtomicInteger();
        user.getRoles().forEach(role -> {
            User newUser = (User) SerializationUtils.clone(user);
            Set<Role> newRoles = new HashSet<>();
            newRoles.add(role);
            newUser.setRoles(newRoles);
            if ((isTimeKeepingUser(newUser) && isTimeKeepingAdmin(currentUser) && userAndCurrentUserBelongOrganization)
                    || (isAreaRestrictionUser(newUser) && isAreaRestrictionAdmin(currentUser) && userAndCurrentUserBelongOrganization)
                    || (isBehaviorUser(newUser) && isBehaviorAdmin(currentUser) && userAndCurrentUserBelongOrganization)
                    || (isTimeKeepingAdmin(newUser) && isSuperAdminOrgTimeKeeping(currentUser) && userAndCurrentUserBelongOrganization)
                    || (isAreaRestrictionAdmin(newUser) && isSuperAdminOrgAreaRestriction(currentUser) && userAndCurrentUserBelongOrganization)
                    || (isBehaviorAdmin(newUser) && isSuperAdminOrgBehavior(currentUser) && userAndCurrentUserBelongOrganization)
                    || (isSuperAdminOrganization(newUser) && isSuperAdmin(currentUser))
            ) {
                number.getAndIncrement();
            }
        });
        return number.get() == user.getRoles().size();
    }

    public String convertFullnameToUsername(String fullname) {
        String nfdNormalizedString = Normalizer.normalize(fullname, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String fullnameEnglish = pattern.matcher(nfdNormalizedString)
                .replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");

        String[] fullnameArray = fullnameEnglish.toLowerCase().replaceAll("\\d+", "").split(" ");
        StringBuilder username = new StringBuilder(fullnameArray[fullnameArray.length - 1]);
        for (int i = 0; i < fullnameArray.length - 1; i++) {
            if (!fullnameArray[i].equals("")) {
                username.append(fullnameArray[i].charAt(0));
            }
        }
        if (username.length() < Const.MIN_LENGTH_USERNAME) {
            Random rand = new Random();
            username.append(generate(() -> List.of("1","2","3","4","5","6","7","8","9","0").get(rand.nextInt(10))).limit(Const.MIN_LENGTH_USERNAME - username.length()).collect(joining()));
        }
        while (existUserByUsername(username.toString())) {
            int random = (int) Math.floor(Math.random() * 100);
            username.append(random);
        }
        return username.toString();
    }

    @Override
    public int getNumberUserOfOrganization(Integer organizationId) {
        return userRepository.countByOrganizationIdAndStatus(organizationId, Const.ACTIVE);
    }

    @Override
    public int getNumberAllUserOfOrganization(Integer organizationId) {
        return userRepository.countByOrganizationIdAndStatusNotIn(organizationId, List.of(Const.DELETED));
    }

    @Override
    public OrganizationDtoClient getOrganizationById(int organizationId) {
        OrganizationResponseClient organizationResponseClient = organizationClient.getOrganization(internalToken, organizationId, Const.INTERNAL).getBody();
        if (organizationResponseClient == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return organizationResponseClient.getOrganization();
    }

    @Override
    public OrganizationDtoClient getOrganizationByName(String organizationName) {
        OrganizationResponseClient organizationResponseClient = organizationClient.getOrganization(internalToken, organizationName, Const.INTERNAL).getBody();
        if (organizationResponseClient == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return organizationResponseClient.getOrganization();
    }

    @Override
    public OrganizationDtoClient addOrganization(Organization organization) {
        OrganizationResponseClient organizationResponseClient = organizationClient.addOrganization(httpServletRequest
                .getHeader("token"), new OrganizationRequestClient(organization), Const.INTERNAL).getBody();
        if (organizationResponseClient == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return organizationResponseClient.getOrganization();
    }

    @Override
    public LocationDtoClient getLocation(Integer locationId) {
        if (locationId == null) {
            throw new AccountRestApiException(UserErrorCode.LOCATION_INVALID);
        }
        String token = internalToken;

        LocationResponseClient locationResponseClient = locationClient.getLocationById(token, locationId, Const.INTERNAL).getBody();
        if (locationResponseClient == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return locationResponseClient.getLocation();
    }

    @Override
    public MetadataDtoClient saveMetadata(MultipartFile file) {
        MetadataResponseClient metadataResponseClient = metadataClient.saveMetadata(internalToken, file, Const.INTERNAL).getBody();
        if (metadataResponseClient == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return metadataResponseClient.getMetadata();
    }

    @Override
    public List<User> getUsersByParentId(int id) {
        return userRepository.findAllByParentId(id);
    }

    @Override
    public List<User> getUsersByParentIdAndStatus(int id) {
        return userRepository.findAllByParentIdAndStatusNotIn(id, List.of(Const.DELETED));
    }

    @Override
    public MetadataDtoClient getMetadata(int id) {
        MetadataResponseClient metadataResponseClient = metadataClient.getMetadata(internalToken, id, Const.INTERNAL).getBody();
        if (metadataResponseClient == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return metadataResponseClient.getMetadata();
    }

    @Override
    public int getNumberUserOfLocation(Integer locationId) {
        return userRepository.countByLocationIdAndStatus(locationId, Const.ACTIVE);
    }

    @Override
    public int getNumberUserOfRoles(Integer organizationId, List<Integer> roleIds) {
        return userRepository.getNumberUserOfRoles(organizationId, roleIds);
    }

    @Override
    public int getNumberUserOfRoles(List<Integer> roleIds) {
        return userRepository.getNumberUserOfRoles(roleIds);
    }

    @Override
    public int getNumberOrganizationOfRoles(List<Integer> roleIds) {
        return userRepository.getNumberOrganizationOfRoles(roleIds);
    }

    @Override
    public boolean isSuperAdmin(User user) {
        return user.getRoles().stream().anyMatch(role -> Objects.equals(role.getName(), Const.ROLE_SUPER_ADMIN));
    }

    public boolean belongOrganization(User user, Integer organizationId) {
        if (organizationId == null) {
            return false;
        }
        return user.getOrganizationId().equals(organizationId);
    }

    public boolean isSuperAdminOrganization(User user) {
        if (user.getUsername().equals(superAdminUsername)) return false;
        if (user.getParent() != null) {
            return Objects.equals(user.getParent().getUsername(), superAdminUsername);
        }
        return false;
    }

    public boolean isTimeKeepingAdmin(User user) {
        Set<Role> roles = user.getRoles();
        if (!isSuperAdminOrganization(user)) {
            return roles.stream().anyMatch(role -> role.getName().equals(Const.ROLE_TIME_KEEPING_ADMIN));
        }
        return false;
    }

    @Override
    public boolean isTimeKeepingUser(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(Const.ROLE_TIME_KEEPING_USER));
    }

    @Override
    public boolean isAreaRestrictionAdmin(User user) {
        Set<Role> roles = user.getRoles();
        if (!isSuperAdminOrganization(user)) {
            return roles.stream().anyMatch(role -> role.getName().equals(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN));
        }
        return false;
    }

    @Override
    public boolean isAreaRestrictionUser(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(Const.ROLE_AREA_RESTRICTION_CONTROL_USER));
    }

    @Override
    public boolean isBehaviorAdmin(User user) {
        Set<Role> roles = user.getRoles();
        if (!isSuperAdminOrganization(user)) {
            return roles.stream().anyMatch(role -> role.getName().equals(Const.ROLE_BEHAVIOR_CONTROL_ADMIN));
        }
        return false;
    }

    @Override
    public boolean isBehaviorUser(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(Const.ROLE_BEHAVIOR_CONTROL_USER));
    }

    @Override
    public boolean isAdmin(User user) { // parent of cadres
        return !user.getUsername().equals(superAdminUsername) && !isSuperAdminOrganization(user)
                && user.getRoles().stream().anyMatch(role -> role.getName().contains(Const.ADMIN));
    }

    @Override
    public boolean isSuperAdminOrgTimeKeeping(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(Const.ROLE_TIME_KEEPING_ADMIN)) &&
                user.getParent().getUsername().equals(superAdminUsername);
    }

    @Override
    public boolean isSuperAdminOrgAreaRestriction(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN)) &&
                user.getParent().getUsername().equals(superAdminUsername);
    }

    @Override
    public boolean isSuperAdminOrgBehavior(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equals(Const.ROLE_BEHAVIOR_CONTROL_ADMIN)) &&
                user.getParent().getUsername().equals(superAdminUsername);
    }

    @Override
    public boolean hasPermissionChildRole(User user, String role, List<User> subUsers) {
        if (isSuperAdminOrganization(user)) {
            for (User subUser : subUsers) {
                if ((role.equals(Const.ROLE_TIME_KEEPING_ADMIN) && isTimeKeepingAdmin(subUser))
                        || (role.equals(Const.ROLE_BEHAVIOR_CONTROL_ADMIN) && isBehaviorAdmin(subUser))
                        || (role.equals(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN) && isAreaRestrictionAdmin(subUser))) {
                    return false;
                }
            }
        } else if (isAdmin(user)) {
            for (User subUser : subUsers) {
                if ((role.equals(Const.ROLE_TIME_KEEPING_ADMIN) && isTimeKeepingUser(subUser))
                        || (role.equals(Const.ROLE_BEHAVIOR_CONTROL_ADMIN) && isBehaviorUser(subUser))
                        || (role.equals(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN) && isAreaRestrictionUser(subUser))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isCadres(User user) {
        if (user.getUsername().equals(superAdminUsername)) return false;
        if (user.getRoles() != null) {
            return user.toString(user.getRoles()).contains(Const.CADRES);
        }
        return false;
    }

    @Override
    public List<User> getUserByLocationIdAndOrganizationId(int locationId, int organizationId, int userId) {
        return userRepository.findAllByLocationIdAndOrganizationIdAndStatusAndIdNot(locationId, organizationId, Const.ACTIVE, userId);
    }

    @Override
    public boolean isRoleOfUser(User user, List<String> rolesStr) { // tested for user is cadre
        boolean check = true;
        for (String role : rolesStr) {
            if (isTimeKeepingUser(user) && !role.contains(Const.ROLE_TIME_KEEPING_USER)
                    || isAreaRestrictionUser(user) && !role.contains(Const.ROLE_AREA_RESTRICTION_CONTROL_USER)
                    || isBehaviorUser(user) && !role.contains(Const.ROLE_BEHAVIOR_CONTROL_USER)) {
                check = false;
            }
        }
        return check;
    }

    @Override
    public int getNumberEmployeeOfLocation(int locationId) {
        CountResponseClient countResponseClient = employeeClient.getNumberEmployeeOfLocation(httpServletRequest.getHeader("token"), locationId).getBody();
        if (countResponseClient == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return countResponseClient.getNumber();
    }

    @Override
    public int checkPermissionAddLocationId(User currentUser, Integer locationId) {
        if (isSuperAdmin(currentUser) || isSuperAdminOrganization(currentUser)) {
            if (locationId != null) {
                throw new AccountRestApiException(UserErrorCode.ADMIN_LOCATION_INVALID);
            }
        } else if (isAdmin(currentUser)) {
            if (locationId == null) {
                throw new AccountRestApiException(UserErrorCode.MISSING_LOCATION_FIELD);
            }
            LocationDtoClient location = getLocation(locationId);
            if (location == null) {
                throw new AccountRestApiException(UserErrorCode.LOCATION_NOT_EXIST);
            } else {
                /* check location belong organization */
                if (!Objects.equals(location.getOrganizationId(), currentUser.getOrganizationId())) {
                    throw new AccountRestApiException(UserErrorCode.LOCATION_NOT_BELONG_ORGANIZATION);
                }
                return locationId;
            }
        }
        return 0;
    }

    @Override
    public int checkPermissionAddOrganizationId(User currentUser, Integer organizationId) {
        if (isSuperAdmin(currentUser)) {
            OrganizationDtoClient organization = getOrganizationById(organizationId);
            if (organization == null) {
                throw new AccountRestApiException(UserErrorCode.ORGANIZATION_NOT_EXIST);
            }
            return organizationId;
        } else {
            if (organizationId != null && !Objects.equals(organizationId, currentUser.getOrganizationId())) {
                throw new AccountRestApiException(UserErrorCode.ORGANIZATION_INVALID);
            }
            return currentUser.getOrganizationId();
        }
    }

    public int checkPermissionUpdateAndGetOrganizationId(User user, String organizationIdStr) {
        int organizationId;
        if (organizationIdStr == null || organizationIdStr.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_ORGANIZATION_FIELD);
        }
        try {
            organizationId = Integer.parseInt(organizationIdStr);
        } catch (Exception e) {
            throw new AccountRestApiException(UserErrorCode.ORGANIZATION_INVALID);
        }
        //Only check if organization of user != input
        if (user.getOrganizationId() == organizationId) {
            return organizationId;
        }
        OrganizationDtoClient organization = getOrganizationById(organizationId);
        if (organization == null) {
            throw new AccountRestApiException(UserErrorCode.ORGANIZATION_NOT_EXIST);
        }
        List<User> subUsers = getUsersByParentId(user.getId());
        if (subUsers.size() > 0) { // unable update organization when current user has subUser
            CommonLogger.error(UserErrorCode.FAIL.getMessage() + ": checkPermissionUpdateAndGetOrganizationId() - subUser exist");
            throw new AccountRestApiException(UserErrorCode.FAIL);
        }
        return organizationId;
    }

    @Override
    public int checkPermissionUpdateAndGetLocationId(User user, String locationIdStr) {
        if (isAdmin(user)) {
            if (locationIdStr == null || locationIdStr.trim().isEmpty()) {
                throw new AccountRestApiException(UserErrorCode.MISSING_LOCATION_FIELD);
            }
            LocationDtoClient location = getLocation(Integer.parseInt(locationIdStr));
            if (location == null) {
                throw new AccountRestApiException(UserErrorCode.LOCATION_NOT_EXIST);
            }
            if (!checkLocationTypeAndRole(null, location.getType())) {
                CommonLogger.error(UserErrorCode.FAIL.getMessage() + ": checkPermissionUpdateAndGetLocationId() " +
                        "- currentUserId: " + user.getId() + " not match type of location_id: " + locationIdStr);
                throw new AccountRestApiException(UserErrorCode.FAIL);
            }
            if (!Objects.equals(location.getOrganizationId(), user.getOrganizationId())) {
                CommonLogger.error(UserErrorCode.PERMISSION_DENIED.getMessage() + ": checkPermissionUpdateAndGetLocationId() " +
                        "- currentUserId: " + user.getId() + " and location not belong organization, location_id: " + locationIdStr);
                throw new AccountRestApiException(UserErrorCode.PERMISSION_DENIED);
            }
            return location.getId();
        }
        return 0;
    }

    @Override
    public boolean checkLocationTypeAndRole(User user, String locationType) {
        if (user == null) {
            String currentUsername = tokenProvider.getUserNameFromJwtToken(httpServletRequest.getHeader("token"));
            User currentUser = getUser(currentUsername);
            return (isTimeKeepingAdmin(currentUser) && Const.TIME_KEEPING.equals(locationType))
                    || (isAreaRestrictionAdmin(currentUser) && Const.AREA_RESTRICTION.equals(locationType))
                    || (isBehaviorAdmin(currentUser) && Const.BEHAVIOR.equals(locationType));
        } else {
            return (isTimeKeepingUser(user) && Const.TIME_KEEPING.equals(locationType))
                    || (isAreaRestrictionUser(user) && Const.AREA_RESTRICTION.equals(locationType))
                    || (isBehaviorUser(user) && Const.BEHAVIOR.equals(locationType));
        }
    }

    @Override
    public Set<Role> checkRoleAddUser(Set<String> rolesStr, User currentUser) {
        Set<Role> roleSet = new HashSet<>();
        if (rolesStr.size() > 0) {
            for (String role : rolesStr) {
                Role newRole;
                if (roleServices.existsByName(role)) {
                    newRole = roleServices.findRoleByName(role);
                    roleSet.add(newRole);
                } else {
                    throw new AccountRestApiException(RoleErrorCode.NOT_EXIST_ROLE);
                }
            }
        }
        if (((isSuperAdmin(currentUser) || isSuperAdminOrganization(currentUser)) && rolesStr.toString().contains(Const.CADRES))
            || (isAdmin(currentUser) && (rolesStr.toString().contains(Const.ADMIN) || roleSet.size() > 1))) {
            throw new AccountRestApiException(UserErrorCode.LIST_ROLE_INVALID);
        }
        return roleSet;
    }

    @Override
    public List<String> buildListRoleForQuery(Set<Role> roles) {
        List<String> rolesStr = new ArrayList<>();
        roles.forEach(role -> {
            rolesStr.add(role.getName());
        });
        return rolesStr;
    }
}
