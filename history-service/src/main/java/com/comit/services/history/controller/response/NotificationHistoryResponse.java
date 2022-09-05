package com.comit.services.history.controller.response;

import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.model.dto.NotificationHistoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotificationHistoryResponse extends BaseResponse {
    @JsonProperty(value = "history")
    private NotificationHistoryDto notificationHistoryDto;

    public NotificationHistoryResponse(
            HistoryErrorCode errorCode,
            NotificationHistoryDto notificationHistoryDto
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.notificationHistoryDto = notificationHistoryDto;
    }
}
