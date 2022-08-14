package com.comit.services.organization.service;

import com.comit.services.organization.client.AccountClient;
import com.comit.services.organization.client.LocationClient;
import com.comit.services.organization.client.data.LocationDtoClient;
import com.comit.services.organization.client.data.UserDtoClient;
import com.comit.services.organization.client.response.CheckRoleResponseClient;
import com.comit.services.organization.client.response.CountUserResponse;
import com.comit.services.organization.client.response.LocationListResponseClient;
import com.comit.services.organization.constant.OrganizationErrorCode;
import com.comit.services.organization.controller.response.UserListResponse;
import com.comit.services.organization.exception.RestApiException;
import com.comit.services.organization.model.entity.Organization;
import com.comit.services.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationServicesImpl implements OrganizationServices {
    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    private AccountClient accountClient;
    @Autowired
    private LocationClient locationClient;
    @Autowired
    private HttpServletRequest httpServletRequest;

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

    @Override
    public boolean hasPermissionManageOrganization() {
        CheckRoleResponseClient checkRoleResponse = accountClient.hasPermissionManageOrganization(httpServletRequest.getHeader("token")).getBody();
        if (checkRoleResponse == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        return checkRoleResponse.getHasRole();
    }

    @Override
    public int getNumberUserOfOrganization(int organizationId) {
        CountUserResponse countUserResponse = accountClient.getNumberUserOfOrganization(httpServletRequest.getHeader("token"), organizationId).getBody();
        if (countUserResponse == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        return countUserResponse.getNumber();
    }

    @Override
    public List<LocationDtoClient> getLocationsByOrganizationId(int organizationId) {
        LocationListResponseClient locationListResponseClient = locationClient.getLocationsByOrganizationId(httpServletRequest.getHeader("token"), organizationId).getBody();
        if (locationListResponseClient == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        return locationListResponseClient.getLocation();
    }
}
