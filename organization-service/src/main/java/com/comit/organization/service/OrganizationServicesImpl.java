package com.comit.organization.service;

import com.comit.organization.constant.OrganizationErrorCode;
import com.comit.organization.exception.RestApiException;
import com.comit.organization.model.entity.Location;
import com.comit.organization.model.entity.Organization;
import com.comit.organization.model.entity.User;
import com.comit.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationServicesImpl implements OrganizationServices {
    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    public List<Organization> getAllOrganization() {
        Iterable<Organization> organizationIterable = organizationRepository.findAll();
        List<Organization> organizations = new ArrayList<>();
        organizationIterable.forEach(organizations::add);
        return organizations;
    }

    @Override
    public Organization getOrganization(int id) {
        return organizationRepository.findById(id);
    }

    @Override
    public Organization getOrganization(String name) {
        return organizationRepository.findByName(name);
    }

    @Override
    public Organization addOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public boolean deleteOrganization(int id) {
        organizationRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean deleteOrganization(String name) {
        organizationRepository.deleteByName(name);
        return true;
    }

    @Override
    public List<Organization> addOrganizationList(List<Organization> organizations) {
        try {
            return (List<Organization>) organizationRepository.saveAll(organizations);
        } catch (Exception e) {
            throw new RestApiException(OrganizationErrorCode.CAN_NOT_IMPORT_DATA);
        }
    }
}
