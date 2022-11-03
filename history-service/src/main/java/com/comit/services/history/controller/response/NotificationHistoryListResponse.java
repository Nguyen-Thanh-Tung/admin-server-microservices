package com.comit.services.history.controller.response;

import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.model.dto.NotificationHistoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotificationHistoryListResponse extends BasePagingResponse {
    @JsonProperty(value = "histories")
    private List<NotificationHistoryDto> notificationHistoryDtos;

    public NotificationHistoryListResponse(
            HistoryErrorCode errorCode,
            List<NotificationHistoryDto> notificationHistoryDtos,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.notificationHistoryDtos = notificationHistoryDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
