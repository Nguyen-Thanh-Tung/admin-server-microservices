package com.comit.services.timeKeeping.controller;

import com.comit.services.timeKeeping.business.ShiftBusiness;
import com.comit.services.timeKeeping.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.controller.request.ShiftRequest;
import com.comit.services.timeKeeping.controller.response.BaseResponse;
import com.comit.services.timeKeeping.controller.response.ShiftListResponse;
import com.comit.services.timeKeeping.controller.response.ShiftResponse;
import com.comit.services.timeKeeping.model.dto.ShiftDto;
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

        return new ResponseEntity<>(new ShiftListResponse(TimeKeepingErrorCode.SUCCESS, shiftDtos), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BaseResponse> getShift(@PathVariable Integer id) {
        ShiftDto shiftDto = shiftBusiness.getShift(id);
        return new ResponseEntity<>(new ShiftResponse(TimeKeepingErrorCode.SUCCESS, shiftDto), HttpStatus.OK);
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
        return new ResponseEntity<>(new ShiftResponse(TimeKeepingErrorCode.SUCCESS, shiftDto), HttpStatus.OK);
    }

    @PostMapping(value = "/location/{locationId}")
    public ResponseEntity<BaseResponse> addShiftsForLocation(@PathVariable Integer locationId) {
        boolean addSuccess = shiftBusiness.addShiftsForLocation(locationId);
        return new ResponseEntity<>(new BaseResponse(addSuccess ? TimeKeepingErrorCode.SUCCESS : TimeKeepingErrorCode.FAIL), HttpStatus.OK);
    }

    @DeleteMapping("/location/{locationId}")
    ResponseEntity<BaseResponse> deleteShiftsOfLocation(@PathVariable Integer locationId) {
        boolean deleteSuccess = shiftBusiness.deleteShiftsOfLocation(locationId);
        return new ResponseEntity<>(new BaseResponse(deleteSuccess ? TimeKeepingErrorCode.SUCCESS : TimeKeepingErrorCode.FAIL), HttpStatus.OK);
    }
}
