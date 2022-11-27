package com.comit.services.account.controller.response;

import com.comit.services.account.constant.UserErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasePagingResponse extends BaseResponse {
    @JsonProperty(value = "current_page")
    protected int currentPage;

    @JsonProperty(value = "total_items")
    protected long totalItems;

    @JsonProperty(value = "total_pages")
    protected int totalPages;

    public BasePagingResponse(int code, String message) {
        super(code, message);
    }

    public BasePagingResponse(UserErrorCode userErrorCode) {
        super(userErrorCode.getCode(), userErrorCode.getMessage());
    }
}
