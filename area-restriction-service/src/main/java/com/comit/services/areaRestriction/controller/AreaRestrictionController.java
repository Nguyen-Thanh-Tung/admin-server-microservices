package com.comit.services.areaRestriction.controller;

import com.comit.services.areaRestriction.business.AreaRestrictionBusiness;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.constant.Const;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionRequest;
import com.comit.services.areaRestriction.controller.response.AreaRestrictionListResponse;
import com.comit.services.areaRestriction.controller.response.AreaRestrictionResponse;
import com.comit.services.areaRestriction.controller.response.BaseResponse;
import com.comit.services.areaRestriction.controller.response.NotificationMethodResponse;
import com.comit.services.areaRestriction.model.dto.AreaRestrictionDto;
import com.comit.services.areaRestriction.model.dto.NotificationMethodDto;
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
}
