package com.comit.services.organization.business;

import com.comit.services.organization.controller.request.OrganizationRequest;
import com.comit.services.organization.model.dto.BaseOrganizationDto;
import com.comit.services.organization.model.dto.OrganizationDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface OrganizationBusiness {
    List<OrganizationDto> getAllOrganization();

    OrganizationDto getOrganization(int id);

    BaseOrganizationDto getOrganizationBase(int id);

    BaseOrganizationDto getOrganizationBase(String search);

    OrganizationDto addOrganization(OrganizationRequest addOrganizationRequest);

    OrganizationDto updateOrganization(int id, OrganizationRequest addOrganizationRequest);

    boolean deleteOrganization(int id);

    List<OrganizationDto> importOrganization(HttpServletRequest httpServletRequest) throws IOException;
}
