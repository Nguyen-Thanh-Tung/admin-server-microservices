package com.comit.services.organization.service;

import com.comit.services.organization.client.data.LocationDtoClient;
import com.comit.services.organization.model.entity.Organization;

import java.util.List;

public interface OrganizationServices {
    List<Organization> getAllOrganization();

    Organization addOrganization(Organization organization);

    Organization getOrganization(int id);

    Organization getOrganization(String name);

    boolean deleteOrganization(int id);

    boolean deleteOrganization(String name);

    List<Organization> addOrganizationList(List<Organization> organizations);

    boolean hasPermissionManageOrganization();

    int getNumberUserOfOrganization(int organizationId);

    List<LocationDtoClient> getLocationsByOrganizationId(int organizationId);
}
