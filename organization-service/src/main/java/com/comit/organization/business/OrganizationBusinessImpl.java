package com.comit.organization.business;

import com.comit.organization.client.AccountClient;
import com.comit.organization.client.LocationClient;
import com.comit.organization.client.response.CheckRoleResponse;
import com.comit.organization.client.response.LocationListResponse;
import com.comit.organization.constant.OrganizationErrorCode;
import com.comit.organization.controller.request.OrganizationRequest;
import com.comit.organization.controller.response.UserListResponse;
import com.comit.organization.exception.RestApiException;
import com.comit.organization.middleware.OrganizationVerifyRequestServices;
import com.comit.organization.model.dto.OrganizationDto;
import com.comit.organization.model.entity.Location;
import com.comit.organization.model.entity.Organization;
import com.comit.organization.service.OrganizationServices;
import com.comit.organization.model.entity.User;
import com.comit.organization.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class OrganizationBusinessImpl implements OrganizationBusiness {
    @Autowired
    private OrganizationServices organizationServices;
    @Autowired
    private OrganizationVerifyRequestServices verifyRequestServices;

    @Value("${system.supperAdmin.organization}")
    private String superAdminOrganization;

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private LocationClient locationClient;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public List<OrganizationDto> getAllOrganization() {
        List<Organization> organizations = organizationServices.getAllOrganization();
        CheckRoleResponse checkRoleResponse = accountClient.hasPermissionManageOrganization(httpServletRequest.getHeader("token")).getBody();
        if (checkRoleResponse == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        if (!checkRoleResponse.getHasRole()) {
//            throw new CommonException(ErrorCode.PERMISSION_DENIED);
            return null;
        }

        List<OrganizationDto> organizationDtos = new ArrayList<>();
        organizations.forEach(organization -> {
            if (!Objects.equals(organization.getName(), superAdminOrganization)) {
                organizationDtos.add(OrganizationDto.convertOrganizationToOrganizationDto(organization));
            }
        });
        return organizationDtos;
    }

    @Override
    public OrganizationDto getOrganization(int id) {
        Organization organization = organizationServices.getOrganization(id);

        CheckRoleResponse checkRoleResponse = accountClient.hasPermissionManageOrganization(httpServletRequest.getHeader("token")).getBody();
        if (checkRoleResponse == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        if (!checkRoleResponse.getHasRole()) {
//            throw new CommonException(ErrorCode.PERMISSION_DENIED);
            return null;
        }

        if (organization != null) {
            return OrganizationDto.convertOrganizationToOrganizationDto(organization);
        } else {
            return null;
        }
    }

    @Override
    public OrganizationDto getOrganization(String name) {
        Organization organization = organizationServices.getOrganization(name);

        CheckRoleResponse checkRoleResponse = accountClient.hasPermissionManageOrganization(httpServletRequest.getHeader("token")).getBody();
        if (checkRoleResponse == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        if (!checkRoleResponse.getHasRole()) {
//            throw new CommonException(ErrorCode.PERMISSION_DENIED);
            return null;
        }

        if (organization != null) {
            return OrganizationDto.convertOrganizationToOrganizationDto(organization);
        } else {
            return null;
        }
    }

    @Override
    public OrganizationDto addOrganization(OrganizationRequest request) {
        verifyRequestServices.verifyAddOrUpdateOrganization(request);
        Organization organization = organizationServices.getOrganization(request.getName());
        if (organization != null) {
            throw new RestApiException(OrganizationErrorCode.ORGANIZATION_EXISTED);
        }
        organization = new Organization();
        organization.setName(request.getName());
        organization.setPhone(request.getPhone());
        organization.setEmail(request.getEmail());
        organization.setAddress(request.getAddress());
        organization.setDescription(request.getDescription());
        Organization newOrganization = organizationServices.addOrganization(organization);
        return OrganizationDto.convertOrganizationToOrganizationDto(newOrganization);
    }

    @Override
    public OrganizationDto updateOrganization(int id, OrganizationRequest request) {
        verifyRequestServices.verifyAddOrUpdateOrganization(request);
        Organization organization = organizationServices.getOrganization(id);
        if (organization == null) {
            throw new RestApiException(OrganizationErrorCode.ORGANIZATION_NOT_EXIST);
        }
        organization.setName(request.getName());
        organization.setEmail(request.getEmail());
        organization.setPhone(request.getPhone());
        organization.setAddress(request.getAddress());
        organization.setDescription(request.getDescription());
        Organization newOrganization = organizationServices.addOrganization(organization);
        return OrganizationDto.convertOrganizationToOrganizationDto(newOrganization);
    }

    @Override
    public boolean deleteOrganization(int id) {
        Organization organization = organizationServices.getOrganization(id);
        if (organization == null) {
            throw new RestApiException(OrganizationErrorCode.ORGANIZATION_NOT_EXIST);
        }

        ResponseEntity<UserListResponse> getUsersByOrganizationIdResponse = accountClient.getUsersByOrganizationId(id);
        List<User> users = Objects.requireNonNull(getUsersByOrganizationIdResponse.getBody()).getUsers();
        if (users.size() > 0) {
            throw new RestApiException(OrganizationErrorCode.CAN_DELETE_ORGANIZATION_HAS_USER);
        }
        LocationListResponse locationListResponse = locationClient.getLocationsByOrganizationId(id).getBody();
        if (locationListResponse == null) {
            throw new RestApiException(OrganizationErrorCode.INTERNAL_ERROR);
        }
        if (locationListResponse.getLocations().size() > 0) {
            throw new RestApiException(OrganizationErrorCode.CAN_DELETE_ORGANIZATION_HAS_LOCATION);
        }
        return organizationServices.deleteOrganization(id);
    }

    @Override
    public List<OrganizationDto> importOrganization(HttpServletRequest httpServletRequest) throws IOException {
        String contentType = httpServletRequest.getContentType();
        if (contentType == null || !contentType.contains("multipart/form-data")) {
            throw new RestApiException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
        }
        if (httpServletRequest instanceof MultipartHttpServletRequest multipartHttpServletRequest) {
            MultipartFile file = multipartHttpServletRequest.getFile("file");

            verifyRequestServices.verifyUploadOrganization(file);
            ExcelUtil excelUtil = new ExcelUtil();
            if (file != null) {
                Map<Integer, List<String>> organizationData = excelUtil.readExcel(file);
                List<Organization> organizations = new ArrayList<>();
                organizationData.forEach((integer, strings) -> {
                    if (integer > 0) {
                        Organization organization = new Organization();
                        organization.setName(strings.get(0));
                        organization.setEmail(strings.get(1));
                        organization.setPhone(strings.get(2));
                        organization.setAddress(strings.get(3));
                        organization.setDescription(strings.get(4));
                        organizations.add(organization);
                    }
                });
                List<Organization> newOrganizations = organizationServices.addOrganizationList(organizations);
                List<OrganizationDto> organizationDtos = new ArrayList<>();
                newOrganizations.forEach(newOrganization -> {
                    organizationDtos.add(OrganizationDto.convertOrganizationToOrganizationDto(newOrganization));
                });
                return organizationDtos;
            }
        }
        return null;
    }
}
