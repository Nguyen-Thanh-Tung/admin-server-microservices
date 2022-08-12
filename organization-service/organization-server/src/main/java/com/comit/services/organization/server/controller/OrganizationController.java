package com.comit.services.organization.server.controller;

import com.comit.services.organization.client.dto.OrganizationDto;
import com.comit.services.organization.client.request.OrganizationRequest;
import com.comit.services.organization.client.response.BaseResponse;
import com.comit.services.organization.client.response.OrganizationListResponse;
import com.comit.services.organization.client.response.OrganizationResponse;
import com.comit.services.organization.server.business.OrganizationBusiness;
import com.comit.services.organization.server.constant.OrganizationErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
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
    public ResponseEntity<BaseResponse> getAllOrganization() {
        List<OrganizationDto> organizationDtos = organizationBusiness.getAllOrganization();
        return new ResponseEntity<>(new OrganizationListResponse(OrganizationErrorCode.SUCCESS.getCode(), OrganizationErrorCode.SUCCESS.getMessage(), organizationDtos), HttpStatus.OK);
    }

    /**
     * Get information organization
     *
     * @param id: id of organization
     * @return OrganizationResponse
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrganizationResponse> getOrganization(@PathVariable int id) {
        OrganizationDto organizationDto = organizationBusiness.getOrganization(id);

        if (organizationDto != null) {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS.getCode(), OrganizationErrorCode.SUCCESS.getMessage(), organizationDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS.getCode(), OrganizationErrorCode.SUCCESS.getMessage(), null), HttpStatus.OK);
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
        return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS.getCode(), OrganizationErrorCode.SUCCESS.getMessage(), organizationDto), HttpStatus.OK);
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
        return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS.getCode(), OrganizationErrorCode.SUCCESS.getMessage(), organizationDto), HttpStatus.OK);
    }

    /**
     * Delete organization
     *
     * @param id: id of organization
     * @return BaseResponse
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> deleteOrganization(@PathVariable int id) {
        boolean deleteSuccess = organizationBusiness.deleteOrganization(id);
        if (deleteSuccess) {
            return new ResponseEntity<>(new BaseResponse(OrganizationErrorCode.SUCCESS.getCode(), OrganizationErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(OrganizationErrorCode.FAIL.getCode(), OrganizationErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }


    /**
     * @param httpServletRequest : HttpServletRequest
     * @return OrganizationListResponse
     */
    @PostMapping(value = "/import")
    public ResponseEntity<BaseResponse> importOrganizations(HttpServletRequest httpServletRequest) throws IOException {
        List<OrganizationDto> organizationDtos = organizationBusiness.importOrganization(httpServletRequest);

        return new ResponseEntity<>(new BaseResponse(OrganizationErrorCode.SUCCESS.getCode(), OrganizationErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
    }

    /**
     * Get information organization
     *
     * @param name: name of organization
     * @return OrganizationResponse
     */
    @GetMapping(value = "/name/{name}")
    public ResponseEntity<BaseResponse> getOrganization(@PathVariable String name) {
        OrganizationDto organizationDto = organizationBusiness.getOrganization(name);

        if (organizationDto != null) {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS.getCode(), OrganizationErrorCode.SUCCESS.getMessage(), organizationDto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new OrganizationResponse(OrganizationErrorCode.SUCCESS.getCode(), OrganizationErrorCode.SUCCESS.getMessage(), null), HttpStatus.OK);
        }
    }
}
