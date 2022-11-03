package com.comit.services.userLog.controller.response;

import com.comit.services.userLog.constant.UserLogErrorCode;
import com.comit.services.userLog.model.dto.UserLogDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserLogListResponse extends BasePagingResponse {
    @JsonProperty(value = "user_logs")
    private List<UserLogDto> userLogDtos;

    public UserLogListResponse(
            UserLogErrorCode errorCode,
            List<UserLogDto> userLogDtos,
            int currentPage,
            long totalItems,
            int totalPages) {
        super();
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.userLogDtos = userLogDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
