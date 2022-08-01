package com.comit.services.account.service;

import com.comit.services.account.model.entity.Location;
import com.comit.services.account.model.entity.Metadata;
import com.comit.services.account.model.entity.Organization;
import com.comit.services.account.model.entity.User;
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

    Organization getOrganizationById(int organizationId);

    Organization getOrganizationByName(String organizationName);

    Organization addOrganization(Organization organization);

    void sendForgetPasswordMail(User user);

    Location getLocation(Integer locationId);

    void sendConfirmCreateUserMail(User newUser);

    Metadata saveMetadata(MultipartFile file);

    List<User> getUsersByParentId(int id);
}
