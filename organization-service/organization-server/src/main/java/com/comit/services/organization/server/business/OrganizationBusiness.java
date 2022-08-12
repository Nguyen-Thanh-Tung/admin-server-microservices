package com.comit.services.organization.server.business;


import com.comit.services.organization.client.dto.OrganizationDto;
import com.comit.services.organization.client.request.OrganizationRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface OrganizationBusiness {
    List<OrganizationDto> getAllOrganization();

    OrganizationDto getOrganization(int id);

    OrganizationDto getOrganization(String search);

    OrganizationDto addOrganization(OrganizationRequest addOrganizationRequest);

    OrganizationDto updateOrganization(int id, OrganizationRequest addOrganizationRequest);

    boolean deleteOrganization(int id);

    List<OrganizationDto> importOrganization(HttpServletRequest httpServletRequest) throws IOException;
}
