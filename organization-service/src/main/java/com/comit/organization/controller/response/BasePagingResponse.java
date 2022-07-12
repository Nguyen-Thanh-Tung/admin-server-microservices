package com.comit.organization.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
