package com.comit.services.organization.service;

import com.comit.services.organization.model.entity.Location;
import com.comit.services.organization.model.entity.Organization;
import com.comit.services.organization.model.entity.User;

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

    List<User> getUsersByOrganizationId(int organizationId);

    List<Location> getLocationsByOrganizationId(int organizationId);
}
