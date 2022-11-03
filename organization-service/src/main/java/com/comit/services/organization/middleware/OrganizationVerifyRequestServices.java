package com.comit.services.organization.middleware;

import com.comit.services.organization.controller.request.OrganizationRequest;
import org.springframework.web.multipart.MultipartFile;

public interface OrganizationVerifyRequestServices {
    void verifyAddOrUpdateOrganization(OrganizationRequest request);

    void verifyUploadOrganization(MultipartFile file);
}
