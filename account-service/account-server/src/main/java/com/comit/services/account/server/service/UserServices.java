package com.comit.services.account.server.service;

import com.comit.services.account.server.model.Organization;
import com.comit.services.account.server.model.User;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.metadata.client.dto.MetadataDto;
import com.comit.services.organization.client.dto.OrganizationDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserServices {

    boolean existUserByUsername(String username);

    boolean existUserByEmail(String email);

    List<User> getAllUser();

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

    List<User> getUsersByOrganization(Integer organizationId);

    OrganizationDto getOrganizationById(int organizationId);

    OrganizationDto getOrganizationByName(String organizationName);

    OrganizationDto addOrganization(Organization organization);

    void sendForgetPasswordMail(User user);

    LocationDto getLocation(Integer locationId);

    void sendConfirmCreateUserMail(User newUser);

    MetadataDto saveMetadata(MultipartFile file);

    List<User> getUsersByParentId(int id);

    MetadataDto getMetadata(int id);

    int getNumberUserOfLocation(Integer locationId);
}
