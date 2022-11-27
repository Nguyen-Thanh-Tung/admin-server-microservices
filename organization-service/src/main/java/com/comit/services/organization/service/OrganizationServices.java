package com.comit.services.organization.service;

import com.comit.services.organization.client.data.LocationDtoClient;
import com.comit.services.organization.model.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationServices {
    Page<Organization> getAllOrganization(String search, Pageable pageable);

    Organization addOrganization(Organization organization);

    Organization getOrganization(int id);

    Organization getOrganization(String name);

    boolean deleteOrganization(int id);

    boolean deleteOrganization(String name);

    List<Organization> addOrganizationList(List<Organization> organizations);

    boolean hasPermissionManageOrganization();

    int getNumberUserOfOrganization(int organizationId);

    List<LocationDtoClient> getLocationsByOrganizationId(int organizationId);
    boolean hasRole(String roleNeedCheck);
}
