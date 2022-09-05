package com.comit.services.history.controller.response;

import com.comit.services.history.constant.HistoryErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckResponse extends BaseResponse {
    @JsonProperty("has_history")
    private boolean hasHistory;

    public CheckResponse(HistoryErrorCode historyErrorCode, boolean hasHistory) {
        this.code = historyErrorCode.getCode();
        this.message = historyErrorCode.getMessage();
        this.hasHistory = hasHistory;
    }
}
