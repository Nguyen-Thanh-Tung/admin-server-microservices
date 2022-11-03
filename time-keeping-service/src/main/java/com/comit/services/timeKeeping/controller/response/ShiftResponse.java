package com.comit.services.timeKeeping.controller.response;

import com.comit.services.timeKeeping.constant.ShiftErrorCode;
import com.comit.services.timeKeeping.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.model.dto.ShiftDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftResponse extends BaseResponse {
    @JsonProperty(value = "shift")
    private ShiftDto shiftDto;

    public ShiftResponse(ShiftErrorCode errorCode, ShiftDto shiftDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.shiftDto = shiftDto;
    }

    public ShiftResponse(TimeKeepingErrorCode errorCode, ShiftDto shiftDto) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.shiftDto = shiftDto;
    }
}
