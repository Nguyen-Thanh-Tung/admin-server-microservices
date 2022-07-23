package com.comit.services.history.controller.response;

import com.comit.services.history.constant.HistoryErrorCode;
import com.comit.services.history.model.dto.InOutHistoryDto;
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
            HistoryErrorCode errorCode,
            List<InOutHistoryDto> inOutHistoryDtos,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.inOutHistoryDtos = inOutHistoryDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
