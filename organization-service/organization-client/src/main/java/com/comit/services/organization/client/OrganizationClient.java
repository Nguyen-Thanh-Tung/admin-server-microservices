package com.comit.services.organization.client;

import com.comit.services.organization.client.request.OrganizationRequest;
import com.comit.services.organization.client.response.OrganizationResponse;

public interface OrganizationClient {
    OrganizationResponse getOrganization(String token, Integer organizationId);

    OrganizationResponse getOrganization(String token, String organizationName);

    OrganizationResponse addOrganization(String token, OrganizationRequest organization);
}
