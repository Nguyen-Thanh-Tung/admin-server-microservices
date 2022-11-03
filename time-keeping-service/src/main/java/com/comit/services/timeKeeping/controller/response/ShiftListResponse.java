package com.comit.services.timeKeeping.controller.response;

import com.comit.services.timeKeeping.constant.TimeKeepingErrorCode;
import com.comit.services.timeKeeping.model.dto.ShiftDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftListResponse extends BaseResponse {
    @JsonProperty("shifts")
    private List<ShiftDto> shiftDtos;


    public ShiftListResponse(
            ShiftResponse errorCode,
            List<ShiftDto> shiftDtos) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.shiftDtos = shiftDtos;
    }

    public ShiftListResponse(
            TimeKeepingErrorCode errorCode,
            List<ShiftDto> shiftDtos) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.shiftDtos = shiftDtos;
    }
}
