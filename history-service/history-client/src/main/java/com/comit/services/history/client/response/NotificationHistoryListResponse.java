package com.comit.services.history.client.response;

import com.comit.services.history.client.dto.NotificationHistoryDto;
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
            int errorCode,
            String errorMessage,
            List<NotificationHistoryDto> notificationHistoryDtos,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.code = errorCode;
        this.message = errorMessage;
        this.notificationHistoryDtos = notificationHistoryDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
