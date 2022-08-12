package com.comit.services.account.server.middleware;

import com.comit.services.account.client.request.AddUserRequest;
import com.comit.services.account.client.request.LockOrUnlockRequest;
import com.comit.services.account.client.request.UpdateRoleForUserRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserVerifyRequestServices {
    void verifyAddUserRequest(AddUserRequest request);

    void verifyUpdateRoleForUserRequest(UpdateRoleForUserRequest request);

    void verifyUploadAvatar(MultipartFile file);

    void verifyLockOrUnlockRequest(LockOrUnlockRequest request);

    void verifyUpdateUserRequest(MultipartFile file, String organizationIdStr, String locationIdStr, String roleStrs, String fullName, String email);

}
