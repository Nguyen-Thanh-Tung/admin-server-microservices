package com.comit.services.organization.business;

import com.comit.services.organization.client.data.LocationDtoClient;
import com.comit.services.organization.constant.OrganizationErrorCode;
import com.comit.services.organization.controller.request.OrganizationRequest;
import com.comit.services.organization.exception.RestApiException;
import com.comit.services.organization.middleware.OrganizationVerifyRequestServices;
import com.comit.services.organization.model.dto.BaseOrganizationDto;
import com.comit.services.organization.model.dto.OrganizationDto;
import com.comit.services.organization.model.entity.Organization;
import com.comit.services.organization.service.OrganizationServices;
import com.comit.services.organization.util.ExcelUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    @Override
    public List<OrganizationDto> getAllOrganization() {
        List<Organization> organizations = organizationServices.getAllOrganization();
        boolean hasRole = organizationServices.hasPermissionManageOrganization();
        if (!hasRole) {
//            throw new CommonException(ErrorCode.PERMISSION_DENIED);
            return null;
        }

        List<OrganizationDto> organizationDtos = new ArrayList<>();
        organizations.forEach(organization -> {
            if (!Objects.equals(organization.getName(), superAdminOrganization)) {
                organizationDtos.add(convertOrganizationToOrganizationDto(organization));
            }
        });
        return organizationDtos;
    }

    @Override
    public OrganizationDto getOrganization(int id) {
        Organization organization = organizationServices.getOrganization(id);

        boolean hasRole = organizationServices.hasPermissionManageOrganization();
        if (!hasRole) {
//            throw new CommonException(ErrorCode.PERMISSION_DENIED);
            return null;
        }

        if (organization != null) {
            return convertOrganizationToOrganizationDto(organization);
        } else {
            return null;
        }
    }

    @Override
    public BaseOrganizationDto getOrganizationBase(int id) {
        Organization organization = organizationServices.getOrganization(id);

        if (organization != null) {
            return convertOrganizationToBaseOrganizationDto(organization);
        } else {
            return null;
        }
    }

    @Override
    public BaseOrganizationDto getOrganizationBase(String name) {
        Organization organization = organizationServices.getOrganization(name);

        if (organization != null) {
            return convertOrganizationToBaseOrganizationDto(organization);
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
        return convertOrganizationToOrganizationDto(newOrganization);
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
        return convertOrganizationToOrganizationDto(newOrganization);
    }

    @Override
    public boolean deleteOrganization(int organizationId) {
        Organization organization = organizationServices.getOrganization(organizationId);
        if (organization == null) {
            throw new RestApiException(OrganizationErrorCode.ORGANIZATION_NOT_EXIST);
        }

        int numberUserOfOrganization = organizationServices.getNumberUserOfOrganization(organizationId);
        if (numberUserOfOrganization > 0) {
            throw new RestApiException(OrganizationErrorCode.CAN_DELETE_ORGANIZATION_HAS_USER);
        }
        List<LocationDtoClient> locationDtoClients = organizationServices.getLocationsByOrganizationId(organizationId);

        if (locationDtoClients.size() > 0) {
            throw new RestApiException(OrganizationErrorCode.CAN_DELETE_ORGANIZATION_HAS_LOCATION);
        }
        return organizationServices.deleteOrganization(organizationId);
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
                    organizationDtos.add(convertOrganizationToOrganizationDto(newOrganization));
                });
                return organizationDtos;
            }
        }
        return null;
    }

    public BaseOrganizationDto convertOrganizationToBaseOrganizationDto(Organization organization) {
        if (organization == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        try {
            return modelMapper.map(organization, BaseOrganizationDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public OrganizationDto convertOrganizationToOrganizationDto(Organization organization) {
        if (organization == null) return null;
        ModelMapper modelMapper = new ModelMapper();
        try {
            OrganizationDto organizationDto = modelMapper.map(organization, OrganizationDto.class);
            int numberUserOfOrganization = organizationServices.getNumberUserOfOrganization(organization.getId());
            organizationDto.setNumberUser(numberUserOfOrganization);
            return organizationDto;
        } catch (Exception e) {
            return null;
        }
    }
}
