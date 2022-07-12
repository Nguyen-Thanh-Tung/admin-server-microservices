package com.comit.organization.middleware;

import com.comit.organization.controller.request.OrganizationRequest;
import org.springframework.web.multipart.MultipartFile;

public interface OrganizationVerifyRequestServices {
    void verifyAddOrUpdateOrganization(OrganizationRequest request);

    void verifyUploadOrganization(MultipartFile file);
}
