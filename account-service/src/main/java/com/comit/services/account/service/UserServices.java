package com.comit.services.account.service;

import com.comit.services.account.client.data.LocationDtoClient;
import com.comit.services.account.client.data.MetadataDtoClient;
import com.comit.services.account.client.data.OrganizationDtoClient;
import com.comit.services.account.model.entity.Organization;
import com.comit.services.account.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserServices {

    boolean existUserByUsername(String username);

    boolean existUserByEmail(String email);

    List<User> getAllUser(String status);

    User getUser(int id);

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

    MetadataDtoClient getMetadata(int id);

    int getNumberUserOfLocation(Integer locationId);

    int getNumberUserOfRoles(Integer organizationId, List<Integer> roleIds);

    int getNumberUserOfRoles(List<Integer> roleIds);

    int getNumberOrganizationOfRoles(List<Integer> roleIds);
}
