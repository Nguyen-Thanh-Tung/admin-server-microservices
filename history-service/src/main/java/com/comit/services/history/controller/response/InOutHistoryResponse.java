package com.comit.services.history.controller.response;

import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.model.dto.InOutHistoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InOutHistoryResponse extends BaseResponse {
    @JsonProperty(value = "history")
    private InOutHistoryDto inOutHistoryDto;

    public InOutHistoryResponse(
            HistoryErrorCode errorCode,
            InOutHistoryDto inOutHistoryDto
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.inOutHistoryDto = inOutHistoryDto;
    }
}
