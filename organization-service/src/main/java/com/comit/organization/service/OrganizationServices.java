package com.comit.organization.service;

import com.comit.organization.model.entity.Organization;

import java.util.List;

public interface OrganizationServices {
    List<Organization> getAllOrganization();

    Organization addOrganization(Organization organization);

    Organization getOrganization(int id);

    Organization getOrganization(String name);

    boolean deleteOrganization(int id);

    boolean deleteOrganization(String name);

    List<Organization> addOrganizationList(List<Organization> organizations);
}
