package com.comit.services.areaRestriction.controller;

import com.comit.services.areaRestriction.business.AreaRestrictionBusiness;
import com.comit.services.areaRestriction.constant.AreaRestrictionErrorCode;
import com.comit.services.areaRestriction.controller.request.AreaRestrictionNotificationRequest;
import com.comit.services.areaRestriction.controller.response.AreaRestrictionResponse;
import com.comit.services.areaRestriction.controller.response.BaseResponse;
import com.comit.services.areaRestriction.model.dto.AreaRestrictionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/area-restriction-notifications")
public class AreaRestrictionNotificationController {

    @Autowired
    private AreaRestrictionBusiness areaRestrictionBusiness;

    /**
     * Setting notification for are restriction by area restriction id
     *
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateAreaRestrictionNotification(@PathVariable Integer id, @RequestBody AreaRestrictionNotificationRequest request) {
        AreaRestrictionDto areaRestrictionDto = areaRestrictionBusiness.updateAreaRestrictionNotification(id, request);
        return new ResponseEntity<>(new AreaRestrictionResponse(AreaRestrictionErrorCode.SUCCESS, areaRestrictionDto), HttpStatus.OK);
    }
}
