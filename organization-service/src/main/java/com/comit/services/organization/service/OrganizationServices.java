package com.comit.services.organization.service;

import com.comit.services.organization.client.data.LocationDto;
import com.comit.services.organization.client.data.UserDto;
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

    List<UserDto> getUsersByOrganizationId(int organizationId);

    List<LocationDto> getLocationsByOrganizationId(int organizationId);
}
