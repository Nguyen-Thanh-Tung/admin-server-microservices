package com.comit.services.organization.server.middleware;

import com.comit.services.organization.client.request.OrganizationRequest;
import org.springframework.web.multipart.MultipartFile;

public interface OrganizationVerifyRequestServices {
    void verifyAddOrUpdateOrganization(OrganizationRequest request);

    void verifyUploadOrganization(MultipartFile file);
}
