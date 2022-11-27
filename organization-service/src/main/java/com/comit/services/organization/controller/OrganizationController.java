package com.comit.services.organization.controller;

import com.comit.services.organization.business.OrganizationBusiness;
import com.comit.services.organization.constant.Const;
import com.comit.services.organization.constant.OrganizationErrorCode;
import com.comit.services.organization.controller.request.OrganizationRequest;
import com.comit.services.organization.controller.response.BaseResponse;
import com.comit.services.organization.controller.response.OrganizationListResponse;
import com.comit.services.organization.controller.response.OrganizationResponse;
import com.comit.services.organization.model.dto.BaseOrganizationDto;
import com.comit.services.organization.model.dto.OrganizationDto;
import com.comit.services.organization.model.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/organizations")
public class OrganizationController {
    @Autowired
    OrganizationBusiness organizationBusiness;

    /**
     * Get all organization
     *
     * @return OrganizationListResponse
     */
    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllOrganization(
            @RequestParam(defaultValue = Const.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Const.DEFAULT_SIZE_PAGE) int size,
            @RequestParam(required = false) String search
    ) {
        Page<Organization> organizations = organizationBusiness.getAllOrganization(page, size, search);
        List<OrganizationDto> organizationDtos = new ArrayList<>();
        int currentPage = 0;
        long totalItems = 0;
        int totalPages = 0;
        if (organizations != null) {
            currentPage = organizations.getNumber();
            totalItems = organizations.getTotalElements();
            totalPages = organizations.getTotalPages();
            organizationDtos = organizationBusiness.getAllOrganization(organizations.getContent());
        }
        return new ResponseEntity<>(new OrganizationListResponse(OrganizationErrorCode.SUCCESS, organizationDtos,
                currentPage, totalItems, totalPages), HttpStatus.OK);
    }

    /**
     * Get information organization
     *
     * @param id: id of organization
     * @return OrganizationResponse
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> getOrganization(@PathVariable int id) {
        OrganizationDto organizationDto = organizationBusiness.getOrganization(id);

        if (organizationDto != null) {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS, organizationDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.FAIL, null), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}/base")
    public ResponseEntity<BaseResponse> getOrganizationBase(@PathVariable int id) {
        BaseOrganizationDto organizationDto = organizationBusiness.getOrganizationBase(id);

        if (organizationDto != null) {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS, organizationDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS, null), HttpStatus.OK);
        }
    }

    /**
     * Add organization
     *
     * @param organizationRequest: OrganizationRequest
     * @return OrganizationResponse
     */
    @PostMapping(value = "")
    public ResponseEntity<BaseResponse> addOrganization(@Valid @RequestBody OrganizationRequest organizationRequest) {
        OrganizationDto organizationDto = organizationBusiness.addOrganization(organizationRequest);
        return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS, organizationDto), HttpStatus.OK);
    }

    /**
     * Update organization
     *
     * @param organizationRequest: OrganizationRequest
     * @return OrganizationResponse
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> updateOrganization(@PathVariable int id, @Valid @RequestBody OrganizationRequest organizationRequest) {
        OrganizationDto organizationDto = organizationBusiness.updateOrganization(id, organizationRequest);
        return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS, organizationDto), HttpStatus.OK);
    }

    /**
     * Delete organization
     *
     * @param id: id of organization
     * @return BaseResponse
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> deleteOrganization(@PathVariable int id) {
        boolean deleteOrganization = organizationBusiness.deleteOrganization(id);
        return new ResponseEntity<>(new BaseResponse(deleteOrganization ? OrganizationErrorCode.SUCCESS : OrganizationErrorCode.FAIL), HttpStatus.OK);
    }


    /**
     * @param httpServletRequest : HttpServletRequest
     * @return OrganizationListResponse
     */
    @PostMapping(value = "/import")
    public ResponseEntity<BaseResponse> importOrganizations(HttpServletRequest httpServletRequest) throws IOException {
        List<OrganizationDto> organizationDtos = organizationBusiness.importOrganization(httpServletRequest);

        return new ResponseEntity<>(new BaseResponse(OrganizationErrorCode.SUCCESS), HttpStatus.OK);
    }

    /**
     * Get information organization
     *
     * @param name: name of organization
     * @return OrganizationResponse
     */
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<BaseResponse> getOrganization(@PathVariable String name) {
        BaseOrganizationDto organizationDto = organizationBusiness.getOrganizationBase(name);

        if (organizationDto != null) {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS, organizationDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS, null), HttpStatus.OK);
        }
    }
}
