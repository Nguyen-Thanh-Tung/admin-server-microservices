package com.comit.services.timeKeeping.server.controller;

import com.comit.services.timeKeeping.client.dto.ShiftDto;
import com.comit.services.timeKeeping.client.request.ShiftRequest;
import com.comit.services.timeKeeping.client.response.BaseResponse;
import com.comit.services.timeKeeping.client.response.ShiftListResponse;
import com.comit.services.timeKeeping.client.response.ShiftResponse;
import com.comit.services.timeKeeping.server.business.ShiftBusiness;
import com.comit.services.timeKeeping.server.constant.TimeKeepingErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shifts")
public class ShiftController {
    @Autowired
    private ShiftBusiness shiftBusiness;

    /**
     * Get all shift
     *
     * @return ShiftListResponse
     */
    @GetMapping(value = "")
    public ResponseEntity<BaseResponse> getAllShift() {
        List<ShiftDto> shiftDtos = shiftBusiness.getAllShift();

        return new ResponseEntity<>(new ShiftListResponse(TimeKeepingErrorCode.SUCCESS.getCode(), TimeKeepingErrorCode.SUCCESS.getMessage(), shiftDtos), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> getShift(@PathVariable Integer id) {
        ShiftDto shiftDto = shiftBusiness.getShift(id);
        return new ResponseEntity<>(new ShiftResponse(TimeKeepingErrorCode.SUCCESS.getCode(), TimeKeepingErrorCode.SUCCESS.getMessage(), shiftDto), HttpStatus.OK);
    }

    /**
     * Update shift
     *
     * @param shiftRequest ShiftRequest
     * @return ShiftResponse
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> updateShift(@PathVariable int id, @RequestBody ShiftRequest shiftRequest) {
        ShiftDto shiftDto = shiftBusiness.updateShift(id, shiftRequest);
        return new ResponseEntity<>(new ShiftResponse(TimeKeepingErrorCode.SUCCESS.getCode(), TimeKeepingErrorCode.SUCCESS.getMessage(), shiftDto), HttpStatus.OK);
    }

    @PostMapping(value = "/location/{locationId}")
    public ResponseEntity<BaseResponse> addShiftsForLocation(@PathVariable Integer locationId) {
        boolean addSuccess = shiftBusiness.addShiftsForLocation(locationId);
        if (addSuccess) {
            return new ResponseEntity<>(new BaseResponse(TimeKeepingErrorCode.SUCCESS.getCode(), TimeKeepingErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(TimeKeepingErrorCode.FAIL.getCode(), TimeKeepingErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }

    @DeleteMapping("/location/{locationId}")
    ResponseEntity<BaseResponse> deleteShiftsOfLocation(@PathVariable Integer locationId) {
        boolean deleteSuccess = shiftBusiness.deleteShiftsOfLocation(locationId);
        if (deleteSuccess) {
            return new ResponseEntity<>(new BaseResponse(TimeKeepingErrorCode.SUCCESS.getCode(), TimeKeepingErrorCode.SUCCESS.getMessage()), HttpStatus.OK);
        }
        return new ResponseEntity<>(new BaseResponse(TimeKeepingErrorCode.FAIL.getCode(), TimeKeepingErrorCode.FAIL.getMessage()), HttpStatus.OK);
    }
}
