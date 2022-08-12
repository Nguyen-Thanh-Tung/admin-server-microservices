package com.comit.services.areaRestriction.server.controller;

import com.comit.services.areaRestriction.client.dto.AreaEmployeeTimeDto;
import com.comit.services.areaRestriction.client.dto.AreaRestrictionDto;
import com.comit.services.areaRestriction.client.dto.AreaRestrictionNotificationDto;
import com.comit.services.areaRestriction.client.dto.NotificationMethodDto;
import com.comit.services.areaRestriction.client.request.AreaEmployeeTimeListRequest;
import com.comit.services.areaRestriction.client.request.AreaRestrictionNotificationRequest;
import com.comit.services.areaRestriction.client.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.client.response.*;
import com.comit.services.areaRestriction.server.business.AreaEmployeeTimeBusiness;
import com.comit.services.areaRestriction.server.business.AreaRestrictionBusiness;
import com.comit.services.areaRestriction.server.business.AreaRestrictionManagerNotificationBusiness;
import com.comit.services.areaRestriction.server.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.server.constant.Const;
import com.comit.services.areaRestriction.server.model.AreaRestriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/area-restrictions")
public class AreaRestrictionController {
    @Autowired
    private AreaRestrictionBusiness areaRestrictionBusiness;
    @Autowired
    private AreaRestrictionManagerNotificationBusiness areaRestrictionManagerNotificationBusiness;
    @Autowired
    private AreaEmployeeTimeBusiness areaEmployeeTimeBusiness;

    /**
     * Get all area restriction
     *
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllAreaRestriction(
            @RequestParam(defaultValue = Const.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Const.DEFAULT_SIZE_PAGE) int size,
            @RequestParam(required = false) String search
    ) {
        Page<AreaRestriction> areaRestrictionPage = areaRestrictionBusiness.getAreaRestrictionPage(page, size, search);
        List<AreaRestrictionDto> areaRestrictionDtos = new ArrayList<>();
        int currentPage = 0;
        long totalItems = 0;
        int totalPages = 0;
        if (areaRestrictionPage != null) {
            areaRestrictionDtos = areaRestrictionBusiness.getAllAreaRestriction(areaRestrictionPage.getContent());
            currentPage = areaRestrictionPage.getNumber();
            totalItems = areaRestrictionPage.getTotalElements();
            totalPages = areaRestrictionPage.getTotalPages();
        }
        return new ResponseEntity<>(new AreaRestrictionListResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), areaRestrictionDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
    }

    /**
     * Add area restriction
     *
     * @param request: AreaRestrictionRequest
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity<BaseResponse> addAreaRestriction(@RequestBody AreaRestrictionRequest request) {
        AreaRestrictionDto areaRestrictionDto = areaRestrictionBusiness.addAreaRestriction(request);
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), areaRestrictionDto), HttpStatus.OK);
    }

    /**
     * Update area restriction
     *
     * @param id       : Integer
     * @param request: AreaRestrictionRequest
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> updateAreaRestriction(@PathVariable Integer id, @RequestBody AreaRestrictionRequest request) {
        AreaRestrictionDto areaRestrictionDto = areaRestrictionBusiness.updateAreaRestriction(id, request);
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), areaRestrictionDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> deleteAreaRestriction(@PathVariable Integer id) {
        boolean deleteAreaRestrictionSuccess = areaRestrictionBusiness.deleteAreaRestriction(id);
        if (deleteAreaRestrictionSuccess) {
            return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.CAN_NOT_DELETE_AREA_RESTRICTION.getCode(), AreaRestrictionErrorCode.CAN_NOT_DELETE_AREA_RESTRICTION.getMessage()), HttpStatus.OK);
    }

    @GetMapping("/{id}/notification-method")
    ResponseEntity<BaseResponse> getNotificationMethodOfAreaRestriction(@PathVariable Integer id) {
        NotificationMethodDto notificationMethodDto = areaRestrictionBusiness.getNotificationMethodOfAreaRestriction(id);
        return new ResponseEntity<>(new NotificationMethodResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), notificationMethodDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse> getAreaRestriction(@PathVariable Integer id) {
        AreaRestrictionDto areaRestrictionDto = areaRestrictionBusiness.getAreaRestriction(id);
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), areaRestrictionDto), HttpStatus.OK);
    }

    @GetMapping("/{id}/notification")
    ResponseEntity<BaseResponse> getNotificationOfAreaRestriction(@PathVariable Integer id) {
        AreaRestrictionNotificationDto areaRestrictionNotificationDto = areaRestrictionBusiness.getAreaRestrictionNotification(id);
        return new ResponseEntity<>(new AreaRestrictionNotificationResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), areaRestrictionNotificationDto), HttpStatus.OK);
    }

    @PutMapping("/{id}/notification")
    ResponseEntity<BaseResponse> updateNotificationOfAreaRestriction(@PathVariable Integer id, @RequestBody AreaRestrictionNotificationRequest areaRestrictionNotificationRequest) {
        AreaRestrictionDto areaRestrictionDto = areaRestrictionBusiness.updateAreaRestrictionNotification(id, areaRestrictionNotificationRequest);
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), areaRestrictionDto), HttpStatus.OK);
    }

    @DeleteMapping("/manager/{managerId}")
    ResponseEntity<BaseResponse> deleteManagerOnAllAreaRestriction(@PathVariable Integer managerId) {
        boolean deleteManagerOnAllAreaRestrictionSuccess = areaRestrictionBusiness.deleteManagerOnAllAreaRestriction(managerId);
        if (deleteManagerOnAllAreaRestrictionSuccess) {
            return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.FAIL.getCode(), AreaRestrictionErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }

    @DeleteMapping("/manager/{employeeId}/notification-manager")
    ResponseEntity<BaseResponse> deleteAreaRestrictionManagerNotificationList(@PathVariable Integer employeeId) {
        boolean deleteARManagerNotificationSuccess = areaRestrictionManagerNotificationBusiness.deleteARManagerNotificationOfEmployee(employeeId);
        if (deleteARManagerNotificationSuccess) {
            return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.FAIL.getCode(), AreaRestrictionErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }

    @PostMapping("/area-employee-times")
    ResponseEntity<BaseResponse> saveAreaEmployeeTimeList(@RequestBody AreaEmployeeTimeListRequest areaEmployeeTimeListRequest) {
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = areaEmployeeTimeBusiness.saveAreaEmployeeTimeList(areaEmployeeTimeListRequest);
        return new ResponseEntity<>(new AreaEmployeeTimeListResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), areaEmployeeTimeDtos), HttpStatus.OK);
    }

    @DeleteMapping("/area-employee-times/employee/{employeeId}")
    ResponseEntity<BaseResponse> deleteAreaEmployeeTimeList(@PathVariable Integer employeeId) {
        boolean deleteSuccess = areaEmployeeTimeBusiness.deleteEmployeeAreaRestrictionList(employeeId);
        if (deleteSuccess) {
            return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.FAIL.getCode(), AreaRestrictionErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }

    @GetMapping("/area-employee-times/employee/{employeeId}")
    ResponseEntity<BaseResponse> getAreaEmployeeTimesOfEmployee(@PathVariable Integer employeeId) {
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = areaEmployeeTimeBusiness.getAreaEmployeeTimeListOfEmployee(employeeId);
        return new ResponseEntity<>(new AreaEmployeeTimeListResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), areaEmployeeTimeDtos), HttpStatus.OK);
    }

    @GetMapping("/{areaRestrictionId}/area-employee-times")
    ResponseEntity<BaseResponse> getAreaEmployeeTimesOfAreaRestriction(@PathVariable Integer areaRestrictionId) {
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = areaEmployeeTimeBusiness.getAreaEmployeeTimeListOfAreaRestriction(areaRestrictionId);
        return new ResponseEntity<>(new AreaEmployeeTimeListResponse(AreaRestrictionErrorCode.SUCCESS.getCode(), AreaRestrictionErrorCode.SUCCESS.getMessage(), areaEmployeeTimeDtos), HttpStatus.OK);
    }
}
