package com.comit.services.areaRestriction.controller;

import com.comit.services.areaRestriction.business.AreaEmployeeTimeBusiness;
import com.comit.services.areaRestriction.business.AreaRestrictionBusiness;
import com.comit.services.areaRestriction.business.AreaRestrictionManagerNotificationBusiness;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.constant.Const;
import com.comit.services.areaRestriction.controller.request.AreaEmployeeTimeListRequest;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionNotificationRequest;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.controller.response.*;
import com.comit.services.areaRestriction.model.dto.*;
import com.comit.services.areaRestriction.model.entity.AreaRestriction;
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
        return new ResponseEntity<>(new AreaRestrictionListResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
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
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionDto), HttpStatus.OK);
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
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> deleteAreaRestriction(@PathVariable Integer id) {
        boolean deleteAreaRestrictionSuccess = areaRestrictionBusiness.deleteAreaRestriction(id);
        if (deleteAreaRestrictionSuccess) {
            return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.SUCCESS), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(AreaRestrictionErrorCode.CAN_NOT_DELETE_AREA_RESTRICTION), HttpStatus.OK);
    }

    @GetMapping("/{id}/notification-method")
    ResponseEntity<BaseResponse> getNotificationMethodOfAreaRestriction(@PathVariable Integer id) {
        NotificationMethodDto notificationMethodDto = areaRestrictionBusiness.getNotificationMethodOfAreaRestriction(id);
        return new ResponseEntity<>(new NotificationMethodResponse(AreaRestrictionErrorCode.SUCCESS, notificationMethodDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse> getAreaRestriction(@PathVariable Integer id) {
        AreaRestrictionDto areaRestrictionDto = areaRestrictionBusiness.getAreaRestriction(id);
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionDto), HttpStatus.OK);
    }

    @GetMapping("/{id}/base")
    ResponseEntity<BaseResponse> getAreaRestrictionBase(@PathVariable Integer id) {
        BaseAreaRestrictionDto areaRestrictionDto = areaRestrictionBusiness.getAreaRestrictionBase(id);
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionDto), HttpStatus.OK);
    }

    @GetMapping("/{id}/notification")
    ResponseEntity<BaseResponse> getNotificationOfAreaRestriction(@PathVariable Integer id) {
        AreaRestrictionNotificationDto areaRestrictionNotificationDto = areaRestrictionBusiness.getAreaRestrictionNotification(id);
        return new ResponseEntity<>(new AreaRestrictionNotificationResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionNotificationDto), HttpStatus.OK);
    }

    @GetMapping("/{id}/notification/base")
    ResponseEntity<BaseResponse> getBaseNotificationOfAreaRestriction(@PathVariable Integer id) {
        BaseAreaRestrictionNotificationDto areaRestrictionNotificationDto = areaRestrictionBusiness.getBaseAreaRestrictionNotification(id);
        return new ResponseEntity<>(new AreaRestrictionNotificationResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionNotificationDto), HttpStatus.OK);
    }

    @PutMapping("/{id}/notification")
    ResponseEntity<BaseResponse> updateNotificationOfAreaRestriction(@PathVariable Integer id, @RequestBody AreaRestrictionNotificationRequest areaRestrictionNotificationRequest) {
        AreaRestrictionDto areaRestrictionDto = areaRestrictionBusiness.updateAreaRestrictionNotification(id, areaRestrictionNotificationRequest);
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionDto), HttpStatus.OK);
    }

    @DeleteMapping("/manager/{managerId}")
    ResponseEntity<BaseResponse> deleteManagerOnAllAreaRestriction(@PathVariable Integer managerId) {
        boolean deleteManagerOnAllAreaRestrictionSuccess = areaRestrictionBusiness.deleteManagerOnAllAreaRestriction(managerId);
        return new ResponseEntity<>(new BaseResponse(deleteManagerOnAllAreaRestrictionSuccess ? AreaRestrictionErrorCode.SUCCESS : AreaRestrictionErrorCode.FAIL), HttpStatus.OK);
    }

    @DeleteMapping("/manager/{employeeId}/notification-manager")
    ResponseEntity<BaseResponse> deleteAreaRestrictionManagerNotificationList(@PathVariable Integer employeeId) {
        boolean deleteARManagerNotificationSuccess = areaRestrictionManagerNotificationBusiness.deleteARManagerNotificationOfEmployee(employeeId);
        return new ResponseEntity<>(new BaseResponse(deleteARManagerNotificationSuccess ? AreaRestrictionErrorCode.SUCCESS : AreaRestrictionErrorCode.FAIL), HttpStatus.OK);
    }

    @PostMapping("/area-employee-times")
    ResponseEntity<BaseResponse> saveAreaEmployeeTimeList(@RequestBody AreaEmployeeTimeListRequest areaEmployeeTimeListRequest) {
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = areaEmployeeTimeBusiness.saveAreaEmployeeTimeList(areaEmployeeTimeListRequest);
        return new ResponseEntity<>(new AreaEmployeeTimeListResponse(AreaRestrictionErrorCode.SUCCESS, areaEmployeeTimeDtos), HttpStatus.OK);
    }

    @DeleteMapping("/area-employee-times/employee/{employeeId}")
    ResponseEntity<BaseResponse> deleteAreaEmployeeTimeList(@PathVariable Integer employeeId) {
        boolean deleteSuccess = areaEmployeeTimeBusiness.deleteEmployeeAreaRestrictionList(employeeId);
        return new ResponseEntity<>(new BaseResponse(deleteSuccess ? AreaRestrictionErrorCode.SUCCESS : AreaRestrictionErrorCode.FAIL), HttpStatus.OK);
    }

    @GetMapping("/area-employee-times/employee/{employeeId}")
    ResponseEntity<BaseResponse> getAreaEmployeeTimesOfEmployee(@PathVariable Integer employeeId) {
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = areaEmployeeTimeBusiness.getAreaEmployeeTimeListOfEmployee(employeeId);
        return new ResponseEntity<>(new AreaEmployeeTimeListResponse(AreaRestrictionErrorCode.SUCCESS, areaEmployeeTimeDtos), HttpStatus.OK);
    }

    @GetMapping("/{areaRestrictionId}/area-employee-times")
    ResponseEntity<BaseResponse> getAreaEmployeeTimesOfAreaRestriction(@PathVariable Integer areaRestrictionId) {
        List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = areaEmployeeTimeBusiness.getAreaEmployeeTimeListOfAreaRestriction(areaRestrictionId);
        return new ResponseEntity<>(new AreaEmployeeTimeListResponse(AreaRestrictionErrorCode.SUCCESS, areaEmployeeTimeDtos), HttpStatus.OK);
    }

    @GetMapping("/manager/{managerId}")
    ResponseEntity<BaseResponse> getAllAreaRestriction(@PathVariable Integer managerId) {
        List<AreaRestrictionDto> areaRestrictionDtos = areaRestrictionBusiness.getAllAreaRestriction(managerId);
        return new ResponseEntity<>(new AreaRestrictionListResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionDtos), HttpStatus.OK);
    }
}
