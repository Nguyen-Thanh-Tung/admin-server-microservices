package com.comit.services.employee.controller.response;

import com.comit.services.employee.constant.GuestErrorCode;
import com.comit.services.employee.model.dto.GuestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestResponse extends BaseResponse {
    @JsonProperty(value = "guest")
    private GuestDto guest;

    public GuestResponse(GuestErrorCode errorCode, GuestDto guestDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.guest = guestDto;
    }
}
