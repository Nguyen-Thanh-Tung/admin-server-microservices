package com.comit.services.location.server.controller;

import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.location.client.request.LocationRequest;
import com.comit.services.location.client.response.BaseResponse;
import com.comit.services.location.client.response.LocationListResponse;
import com.comit.services.location.client.response.LocationResponse;
import com.comit.services.location.server.business.LocationBusiness;
import com.comit.services.location.server.constant.Const;
import com.comit.services.location.server.constant.LocationErrorCode;
import com.comit.services.location.server.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private LocationBusiness locationBusiness;

    /**
     * Get all location
     *
     * @return LocationListResponse
     */
    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllLocation(
            @RequestParam(defaultValue = Const.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Const.DEFAULT_SIZE_PAGE) int size,
            @RequestParam(required = false) String search
    ) {
        Page<Location> locationPage = locationBusiness.getLocationPage(page, size, search);
        List<LocationDto> locationDtos = new ArrayList<>();
        int currentPage = 0;
        long totalItems = 0;
        int totalPages = 0;
        if (locationPage != null) {
            locationDtos = locationBusiness.getAllLocation(locationPage.getContent());
            currentPage = locationPage.getNumber();
            totalItems = locationPage.getTotalElements();
            totalPages = locationPage.getTotalPages();
        }
        return new ResponseEntity<>(new LocationListResponse(LocationErrorCode.SUCCESS.getCode(), LocationErrorCode.SUCCESS.getMessage(), locationDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> getLocation(@PathVariable int id) {
        LocationDto locationDto = locationBusiness.getLocation(id);
        return new ResponseEntity<>(new LocationResponse(LocationErrorCode.SUCCESS.getCode(), LocationErrorCode.SUCCESS.getMessage(), locationDto), HttpStatus.OK);
    }

    /**
     * Add location
     *
     * @param locationRequest LocationRequest
     * @return LocationResponse
     */
    @PostMapping(value = "")
    public ResponseEntity<BaseResponse> addLocation(@RequestBody LocationRequest locationRequest) {
        LocationDto locationDto = locationBusiness.addLocation(locationRequest);
        return new ResponseEntity<>(new LocationResponse(LocationErrorCode.SUCCESS.getCode(), LocationErrorCode.SUCCESS.getMessage(), locationDto), HttpStatus.OK);
    }

    /**
     * Update location
     *
     * @param locationRequest LocationRequest
     * @return LocationResponse
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> updateLocation(@PathVariable int id, @RequestBody LocationRequest locationRequest) {
        LocationDto locationDto = locationBusiness.updateLocation(id, locationRequest);
        return new ResponseEntity<>(new LocationResponse(LocationErrorCode.SUCCESS.getCode(), LocationErrorCode.SUCCESS.getMessage(), locationDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> deleteLocation(@PathVariable int id) {
        boolean deleteSuccess = locationBusiness.deleteLocation(id);
        if (deleteSuccess) {
            return new ResponseEntity<>(new BaseResponse(LocationErrorCode.SUCCESS.getCode(), LocationErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(LocationErrorCode.CAN_NOT_DELETE_LOCATION.getCode(), LocationErrorCode.CAN_NOT_DELETE_LOCATION.getMessage()), HttpStatus.OK);
    }

    @GetMapping("/organization/{organizationId}")
    ResponseEntity<LocationListResponse> getLocationsByOrganizationId(@PathVariable int organizationId) {
        List<LocationDto> locationDtos = locationBusiness.getAllLocationByOrganizationId(organizationId);
        return new ResponseEntity<>(new LocationListResponse(LocationErrorCode.SUCCESS.getCode(), LocationErrorCode.SUCCESS.getMessage(), locationDtos, 0, locationDtos.size(), 1), HttpStatus.OK);
    }
}
