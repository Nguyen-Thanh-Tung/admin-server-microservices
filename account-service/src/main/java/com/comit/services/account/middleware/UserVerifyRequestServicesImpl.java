package com.comit.services.account.middleware;

import com.comit.services.account.constant.Const;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.model.entity.User;
import com.comit.services.account.service.UserServices;
import com.comit.services.account.util.ValidateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class UserVerifyRequestServicesImpl implements UserVerifyRequestServices {
    @Autowired
    private ValidateField validateField;

    @Autowired
    UserServices userServices;

    @Override
    public void verifyAddUserRequest(AddUserRequest request) {
        String fullname = request.getFullname();
        String email = request.getEmail();
        Integer organizationId = request.getOrganizationId();
        Integer locationId = request.getLocationId();
        Set<String> roles = request.getRoles();

        /* fullName*/
        if (fullname == null || fullname.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_FULLNAME_FIELD);
        } else if (!validateField.validFullName(fullname)) {
            throw new AccountRestApiException(UserErrorCode.FULLNAME_IN_VALID);
        } else {
            request.setFullname(fullname.trim());
        }

        /* email */
        if (email == null || email.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_EMAIL_FIELD);
        } else if (!validateField.validEmail(email)) {
            throw new AccountRestApiException(UserErrorCode.EMAIL_INVALID);
        } else {
            request.setEmail(email.trim());
        }

        /* organizationId */
        if (organizationId == null) {
            throw new AccountRestApiException(UserErrorCode.MISSING_ORGANIZATION_FIELD);
        } else if (organizationId < 0) {
            throw new AccountRestApiException(UserErrorCode.ORGANIZATION_INVALID);
        }

        /* locationId */
        if (locationId != null && locationId < 0) { // require with admin create cadre
            throw new AccountRestApiException(UserErrorCode.LOCATION_INVALID);
        }

        /* roles */
        if (roles == null || roles.size() == 0) {
            throw new AccountRestApiException(UserErrorCode.MISSING_ROLE_FIELD);
        } else {
            Set<String> newRoles = new HashSet<>();
            for (String role : roles) {
                if (!role.trim().equals("")) {
                    newRoles.add(role.trim());
                }
            }
            if (newRoles.size() == 0) {
                throw new AccountRestApiException(UserErrorCode.LIST_ROLE_INVALID);
            } else if (newRoles.toString().contains(Const.ADMIN) && newRoles.toString().contains(Const.CADRES)) {
                throw new AccountRestApiException(UserErrorCode.LIST_ROLE_INVALID);
            }
            request.setRoles(newRoles);
        }
    }

    @Override
    public void verifyUploadAvatar(MultipartFile file) {
        validateField.validFile(file, Const.IMAGE_TYPE);
    }

    @Override
    public void verifyLockOrUnlockRequest(LockOrUnlockRequest request) {
        if (request.getIsLock() == null) {
            throw new AccountRestApiException(UserErrorCode.MISSING_IS_LOCK_FIELD);
        }
    }

    @Override
    public void verifyUpdateUserRequest(MultipartFile file, String roleStrs, String fullname, String email, User user, String locationIdStr) {
        if (file != null) {
            validateField.validFile(file, Const.IMAGE_TYPE);
        }

        if (fullname == null || fullname.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_FULLNAME_FIELD);
        } else if (!validateField.validFullName(fullname)) {
            throw new AccountRestApiException(UserErrorCode.FULLNAME_IN_VALID);
        } else {
            user.setFullName(fullname.trim());
        }

        if (roleStrs == null || roleStrs.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_ROLE_FIELD);
        }

        // RoleStr valid example: ["ABC","XYZ"]
        if (!validateField.validStringArray(roleStrs)) {
            throw new AccountRestApiException(UserErrorCode.LIST_ROLE_INVALID);
        }
        if (email == null || email.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_EMAIL_FIELD);
        }
        if (!validateField.validEmail(email)) {
            throw new AccountRestApiException(UserErrorCode.EMAIL_INVALID);
        }

        if (locationIdStr != null && !locationIdStr.trim().isEmpty()) {
            try {
                Integer.parseInt(locationIdStr);
            } catch (Exception e) {
                throw new AccountRestApiException(UserErrorCode.LOCATION_INVALID);
            }
        }
    }

}
