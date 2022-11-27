package com.comit.services.account.middleware;

import com.comit.services.account.controller.request.AddUserRequest;
import com.comit.services.account.controller.request.LockOrUnlockRequest;
import com.comit.services.account.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserVerifyRequestServices {
    void verifyAddUserRequest(AddUserRequest request);

    void verifyUploadAvatar(MultipartFile file);

    void verifyLockOrUnlockRequest(LockOrUnlockRequest request);

    void verifyUpdateUserRequest(MultipartFile file, String roleStrs, String fullName, String email, User user, String locationIdStr);

}
