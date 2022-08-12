package com.comit.services.account.server.service;

import com.comit.services.account.server.constant.Const;
import com.comit.services.account.server.constant.UserErrorCode;
import com.comit.services.account.server.exeption.AccountRestApiException;
import com.comit.services.account.server.model.Organization;
import com.comit.services.account.server.model.Role;
import com.comit.services.account.server.model.User;
import com.comit.services.account.server.repository.UserRepository;
import com.comit.services.account.server.util.RequestHelper;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.location.client.response.LocationResponse;
import com.comit.services.mail.client.MailClient;
import com.comit.services.mail.client.request.MailCreateUserRequest;
import com.comit.services.mail.client.request.MailForgetPasswordRequest;
import com.comit.services.metadata.client.MetadataClient;
import com.comit.services.metadata.client.dto.MetadataDto;
import com.comit.services.metadata.client.response.MetadataResponse;
import com.comit.services.organization.client.OrganizationClient;
import com.comit.services.organization.client.dto.OrganizationDto;
import com.comit.services.organization.client.request.OrganizationRequest;
import com.comit.services.organization.client.response.OrganizationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RequestHelper requestHelper;
    @Autowired
    MailClient mailClient;
    @Autowired
    OrganizationClient organizationClient;
    @Autowired
    LocationClient locationClient;
    @Autowired
    MetadataClient metadataClient;
    @Autowired
    HttpServletRequest httpServletRequest;

    @Value("${system.supperAdmin.username}")
    private String superAdminUsername;

    public boolean existUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existUserByEmail(String email) {
        return userRepository.existsByEmailAndStatusNotIn(email, List.of(Const.DELETED));
    }

    public List<User> getAllUser() {
        Iterable<User> userIterable = userRepository.findAllByStatusNotIn(List.of(Const.DELETED));
        List<User> users = new ArrayList<>();
        userIterable.forEach(users::add);
        return users;
    }

    public User getUser(int id) {
        return userRepository.findByIdAndStatusNotIn(id, List.of(Const.DELETED));
    }

    public User getUser(String username) {
        return userRepository.findByUsernameAndStatusNotIn(username, List.of(Const.DELETED));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndStatusNotIn(email, List.of(Const.DELETED));
    }

    public User saveUser(User currentUser, User user) {
        if (user != null && hasPermissionManageUser(currentUser, user)) {
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
        for (Role role :
                user.getRoles()) {
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

        // Can manage admin if I am super admin and user is super admin organization OR I am super admin organization and user not super admin organization
        if (hasRole(user, Const.ROLE_TIME_KEEPING_ADMIN)
                || hasRole(user, Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN)
        ) {
            return (requestHelper.hasRole(Const.ROLE_SUPER_ADMIN)
                    && isSuperAdminOrganization(user))
                    || (isSuperAdminOrganization(currentUser)
                    && !isSuperAdminOrganization(user)
                    && userAndCurrentUserBelongOrganization);
        }

        // Can manage user time keeping if I am only admin time keeping admin
        if (hasRole(user, Const.ROLE_TIME_KEEPING_USER)) {
            return requestHelper.hasRole(Const.ROLE_TIME_KEEPING_ADMIN)
                    && !isSuperAdminOrganization(currentUser)
                    && userAndCurrentUserBelongOrganization;
        }
        if (hasRole(user, Const.ROLE_AREA_RESTRICTION_CONTROL_USER)) {
            return requestHelper.hasRole(Const.ROLE_AREA_RESTRICTION_CONTROL_ADMIN)
                    && !isSuperAdminOrganization(currentUser)
                    && userAndCurrentUserBelongOrganization;
        }
        return false;
    }

    public String convertFullnameToUsername(String fullname) {
        String nfdNormalizedString = Normalizer.normalize(fullname, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String fullnameEnglish = pattern.matcher(nfdNormalizedString).replaceAll("");

        String[] fullnameArray = fullnameEnglish.toLowerCase().split(" ");
        StringBuilder username = new StringBuilder(fullnameArray[fullnameArray.length - 1]);
        for (int i = 0; i < fullnameArray.length - 1; i++) {
            username.append(fullnameArray[i].charAt(0));
        }
        while (existUserByUsername(username.toString())) {
            int random = (int) Math.floor(Math.random() * 100);
            username.append(random);
        }
        return username.toString();
    }

    @Override
    public List<User> getUsersByOrganization(Integer organizationId) {
        return userRepository.findAllByOrganizationIdAndStatusNotIn(organizationId, List.of(Const.DELETED));
    }

    @Override
    public OrganizationDto getOrganizationById(int organizationId) {
        OrganizationResponse organizationResponse = organizationClient.getOrganization(httpServletRequest.getHeader("token"), organizationId);
        if (organizationResponse == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return organizationResponse.getOrganization();
    }

    @Override
    public OrganizationDto getOrganizationByName(String organizationName) {
        OrganizationResponse organizationResponse = organizationClient.getOrganization(httpServletRequest.getHeader("token"), organizationName);
        if (organizationResponse == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return organizationResponse.getOrganization();
    }

    @Override
    public OrganizationDto addOrganization(Organization organization) {
        OrganizationRequest organizationRequest = new OrganizationRequest(organization.getName(), organization.getEmail(), organization.getPhone(), organization.getAddress(), organization.getDescription());
        OrganizationResponse organizationResponse = organizationClient.addOrganization(httpServletRequest.getHeader("token"), organizationRequest);
        if (organizationResponse == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return organizationResponse.getOrganization();
    }

    @Override
    public void sendForgetPasswordMail(User user) {
        MailForgetPasswordRequest mailRequest = new MailForgetPasswordRequest(user.getEmail(), user.getFullName(), user.getId(), user.getCode());
        mailClient.sendMailForgetPassword(httpServletRequest.getHeader("token"), mailRequest);
    }

    @Override
    public LocationDto getLocation(Integer locationId) {
        if (locationId == null) {
            return null;
        }
        String token;
        if (httpServletRequest.getHeader("token") != null) {
            token = httpServletRequest.getHeader("token");
        } else {
            token = httpServletRequest.getAttribute("token").toString();
        }
        LocationResponse locationResponse = locationClient.getLocationById(token, locationId);
        if (locationResponse == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return locationResponse.getLocation();
    }

    @Override
    public void sendConfirmCreateUserMail(User newUser) {
        MailCreateUserRequest mailRequest = new MailCreateUserRequest(newUser.getEmail(), newUser.getFullName(), newUser.getId(), newUser.getCode());
        mailClient.sendMailConfirmCreateUser(httpServletRequest.getHeader("token"), mailRequest);
    }

    @Override
    public MetadataDto saveMetadata(MultipartFile file) {
        MetadataResponse metadataResponse = metadataClient.saveMetadata(httpServletRequest.getHeader("token"), file);
        if (metadataResponse == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return metadataResponse.getMetadata();
    }

    @Override
    public List<User> getUsersByParentId(int id) {
        return userRepository.findAllByParentId(id);
    }

    @Override
    public MetadataDto getMetadata(int id) {
        MetadataResponse metadataResponse = metadataClient.getMetadata(httpServletRequest.getHeader("token"), id);
        if (metadataResponse == null) {
            throw new AccountRestApiException(UserErrorCode.INTERNAL_ERROR);
        }
        return metadataResponse.getMetadata();
    }

    @Override
    public int getNumberUserOfLocation(Integer locationId) {
        return userRepository.countByLocationIdAndStatus(locationId, Const.ACTIVE);
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
}
