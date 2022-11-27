package com.comit.services.account.service;

import com.comit.services.account.client.data.LocationDtoClient;
import com.comit.services.account.client.data.MetadataDtoClient;
import com.comit.services.account.client.data.OrganizationDtoClient;
import com.comit.services.account.model.entity.Organization;
import com.comit.services.account.model.entity.Role;
import com.comit.services.account.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface UserServices {

    boolean existUserByUsername(String username);

    boolean existUserByEmail(String email);

    List<User> getAllUser(String status);

    Page<User> getAllUser(String status, String search, Pageable pageable, User user, String role);

    List<User> getAllUserByParentId(String status, int id);

    User getUser(int id);

    List<User> getAllUserByParentId(int id);

    User getUser(String username);

    User getUserByEmail(String email);

    User saveUser(User currentUser, User user);

    User saveUser(User user);

    boolean deleteUser(int id);

    boolean deleteUser(String username);

    boolean hasRole(int id, String roleName);

    boolean hasRole(User user, String roleName);

    boolean hasPermissionManageUser(User currentUser, User user);

    String convertFullnameToUsername(String fullname);

    int getNumberUserOfOrganization(Integer organizationId);

    int getNumberAllUserOfOrganization(Integer organizationId);

    OrganizationDtoClient getOrganizationById(int organizationId);

    OrganizationDtoClient getOrganizationByName(String organizationName);

    OrganizationDtoClient addOrganization(Organization organization);

    LocationDtoClient getLocation(Integer locationId);

    MetadataDtoClient saveMetadata(MultipartFile file);

    List<User> getUsersByParentId(int id);

    List<User> getUsersByParentIdAndStatus(int id);

    MetadataDtoClient getMetadata(int id);

    int getNumberUserOfLocation(Integer locationId);

    int getNumberUserOfRoles(Integer organizationId, List<Integer> roleIds);

    int getNumberUserOfRoles(List<Integer> roleIds);

    int getNumberOrganizationOfRoles(List<Integer> roleIds);

    boolean isSuperAdmin(User user);

    boolean isSuperAdminOrganization(User user);

    boolean isTimeKeepingAdmin(User user);

    boolean isTimeKeepingUser(User user);

    boolean isAreaRestrictionAdmin(User user);

    boolean isAreaRestrictionUser(User user);

    boolean isBehaviorAdmin(User user);

    boolean isBehaviorUser(User user);

    boolean isAdmin(User user);

    boolean isSuperAdminOrgTimeKeeping(User user);

    boolean isSuperAdminOrgAreaRestriction(User user);

    boolean isSuperAdminOrgBehavior(User user);

    boolean hasPermissionChildRole(User user, String role, List<User> subUsers);

    boolean isCadres(User user);

    List<User> getUserByLocationIdAndOrganizationId(int locationId, int organizationId, int userId);

    boolean isRoleOfUser(User user, List<String> role);

    int getNumberEmployeeOfLocation(int locationId);

    int checkPermissionAddLocationId(User currentUser, Integer locationId);

    int checkPermissionAddOrganizationId(User currentUser, Integer organizationId);

    int checkPermissionUpdateAndGetOrganizationId(User user, String organizationIdStr);

    int checkPermissionUpdateAndGetLocationId(User user, String locationIdStr);

    boolean checkLocationTypeAndRole(User user, String locationType);

    Set<Role> checkRoleAddUser(Set<String> rolesStr, User user);

    List<String> buildListRoleForQuery(Set<Role> roles);
}
