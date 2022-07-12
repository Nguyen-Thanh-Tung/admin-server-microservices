package com.comit.services.account.middleware;

import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.controller.request.UpdateRoleForUserRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserVerifyRequestServices {
    void verifyAddUserRequest(AddUserRequest request);

    void verifyUpdateRoleForUserRequest(UpdateRoleForUserRequest request);

    void verifyUploadAvatar(MultipartFile file);

    void verifyLockOrUnlockRequest(LockOrUnlockRequest request);

    void verifyUpdateUserRequest(MultipartFile file, String organizationIdStr, String locationIdStr, String roleStrs, String fullName, String email);

}
