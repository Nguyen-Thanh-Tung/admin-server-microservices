package com.comit.services.employee.controller;

import com.comit.services.employee.business.GuestBusiness;
import com.comit.services.employee.constant.GuestErrorCode;
import com.comit.services.employee.controller.request.GuestRequest;
import com.comit.services.employee.controller.response.BaseResponse;
import com.comit.services.employee.controller.response.GuestResponse;
import com.comit.services.employee.model.dto.GuestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guests")
public class GuestController {
    @Autowired
    private GuestBusiness guestBusiness;

    @PostMapping("")
    ResponseEntity<BaseResponse> addGuest(@RequestBody GuestRequest request) {
        GuestDto guestDto = guestBusiness.addGuest(request);
        return new ResponseEntity<>(new GuestResponse(GuestErrorCode.SUCCESS, guestDto), HttpStatus.OK);
    }
}
