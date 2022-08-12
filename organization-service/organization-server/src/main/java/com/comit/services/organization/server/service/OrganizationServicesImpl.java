package com.comit.services.organization.server.service;

import com.comit.services.account.client.AccountClient;
import com.comit.services.account.client.dto.UserDto;
import com.comit.services.account.client.response.UserListResponse;
import com.comit.services.location.client.LocationClient;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.location.client.response.LocationListResponse;
import com.comit.services.organization.server.constant.OrganizationErrorCode;
import com.comit.services.organization.server.exception.RestApiException;
import com.comit.services.organization.server.model.Organization;
import com.comit.services.organization.server.repository.OrganizationRepository;
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
//        CheckRoleResponse checkRoleResponse = accountClient.hasPermissionManageOrganization(httpServletRequest.getHeader("token")).getBody();
//        if (checkRoleResponse == null) {
//            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
//        }
//        return checkRoleResponse.getHasRole();
        return true;
    }

    @Override
    public List<UserDto> getUsersByOrganizationId(int organizationId) {
        UserListResponse userListResponse = accountClient.getUsersByOrganizationId(httpServletRequest.getHeader("token"), organizationId);
        if (userListResponse == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        return userListResponse.getUserDtos();
    }

    @Override
    public List<LocationDto> getLocationsByOrganizationId(int organizationId) {
        LocationListResponse locationListResponse = locationClient.getLocationsByOrganizationId(httpServletRequest.getHeader("token"), organizationId);
        if (locationListResponse == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        return locationListResponse.getLocations();
    }
}
