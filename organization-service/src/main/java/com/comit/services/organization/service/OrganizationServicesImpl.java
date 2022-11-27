package com.comit.services.organization.service;

import com.comit.services.organization.client.AccountClient;
import com.comit.services.organization.client.LocationClient;
import com.comit.services.organization.client.data.LocationDtoClient;
import com.comit.services.organization.client.response.CheckRoleResponseClient;
import com.comit.services.organization.client.response.CountUserResponse;
import com.comit.services.organization.client.response.LocationListResponseClient;
import com.comit.services.organization.constant.Const;
import com.comit.services.organization.constant.OrganizationErrorCode;
import com.comit.services.organization.exception.RestApiException;
import com.comit.services.organization.model.entity.Organization;
import com.comit.services.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    @Value("${app.internalToken}")
    private String internalToken;

    @Value("${system.supperAdmin.organization}")
    private String superAdminOrganization;

    @Override
    public Page<Organization> getAllOrganization(String search, Pageable pageable) {
        Page<Organization> organizations;
        if (search != null && !search.trim().isEmpty()) {
            organizations = organizationRepository.findAllByNameIsNotAndEmailContainingOrNameIsNotAndNameContainingOrNameIsNotAndPhoneContainingOrderByIdDesc(
                    superAdminOrganization, search, superAdminOrganization, search, superAdminOrganization, search, pageable);
        } else {
            organizations = organizationRepository.findAllByNameNotInOrderByIdDesc(List.of(superAdminOrganization), pageable);
        }
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
        return hasRole(Const.SUPER_ADMIN);
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
        LocationListResponseClient locationListResponseClient = locationClient.getLocationsByOrganizationId(internalToken, organizationId).getBody();
        if (locationListResponseClient == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        return locationListResponseClient.getLocation();
    }

    @Override
    public boolean hasRole(String roleNeedCheck) {
        CheckRoleResponseClient checkRoleResponseClient = accountClient.hasRole(httpServletRequest.getHeader("token"), roleNeedCheck).getBody();
        if (checkRoleResponseClient != null) {
            return checkRoleResponseClient.getHasRole();
        }
        return false;
    }
}
