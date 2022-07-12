package com.comit.services.account.middleware;

import com.comit.services.account.constant.Const;
import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.controller.request.UpdateRoleForUserRequest;
import com.comit.services.account.constant.UserErrorCode;
import com.comit.services.account.exeption.AccountRestApiException;
import com.comit.services.account.util.ValidateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class UserVerifyRequestServicesImpl implements UserVerifyRequestServices {
    @Autowired
    private ValidateField validateField;

    @Override
    public void verifyAddUserRequest(AddUserRequest request) {
        String fullname = request.getFullname();
        String email = request.getEmail();
        Integer organizationId = request.getOrganizationId();
        Set<String> roles = request.getRoles();
        Integer locationId = request.getLocationId();

        if (fullname == null || fullname.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_FULLNAME_FIELD);
        }

        if (email == null || email.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_EMAIL_FIELD);
        }

        if (organizationId != null && organizationId < 0) {
            throw new AccountRestApiException(UserErrorCode.ORGANIZATION_INVALID);
        }

        if (locationId != null && locationId < 0) {
            throw new AccountRestApiException(UserErrorCode.LOCATION_INVALID);
        }

        if (!validateField.validEmail(email)) {
            throw new AccountRestApiException(UserErrorCode.EMAIL_INVALID);
        }
    }

    @Override
    public void verifyUpdateRoleForUserRequest(UpdateRoleForUserRequest request) {

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
    public void verifyUpdateUserRequest(MultipartFile file, String organizationIdStr, String locationIdStr, String roleStrs, String fullname, String email) {
        if (file != null) {
            validateField.validFile(file, Const.IMAGE_TYPE);
        }

        if (fullname == null || fullname.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_FULLNAME_FIELD);
        }

        if (email == null || email.trim().isEmpty()) {
            throw new AccountRestApiException(UserErrorCode.MISSING_EMAIL_FIELD);
        }

        // RoleStr valid example: ["ABC","XYZ"]
        if (!validateField.validStringArray(roleStrs)) {
            throw new AccountRestApiException(UserErrorCode.LIST_ROLE_INVALID);
        }
        if (organizationIdStr != null) {
            try {
                Integer.parseInt(organizationIdStr);
            } catch (Exception e) {
                throw new AccountRestApiException(UserErrorCode.ORGANIZATION_INVALID);
            }
        }

        if (locationIdStr != null && !locationIdStr.trim().isEmpty()) {
            try {
                Integer.parseInt(locationIdStr);
            } catch (Exception e) {
                throw new AccountRestApiException(UserErrorCode.LOCATION_INVALID);
            }
        }

        if (!validateField.validEmail(email)) {
            throw new AccountRestApiException(UserErrorCode.EMAIL_INVALID);
        }
    }

}
