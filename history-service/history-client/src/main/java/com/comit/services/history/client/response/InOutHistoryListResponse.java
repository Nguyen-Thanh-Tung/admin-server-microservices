package com.comit.services.history.client.response;

import com.comit.services.history.client.dto.InOutHistoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InOutHistoryListResponse extends BasePagingResponse {
    @JsonProperty(value = "histories")
    private List<InOutHistoryDto> inOutHistoryDtos;

    public InOutHistoryListResponse(
            int errorCode,
            String errorMessage,
            List<InOutHistoryDto> inOutHistoryDtos,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.code = errorCode;
        this.message = errorMessage;
        this.inOutHistoryDtos = inOutHistoryDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
