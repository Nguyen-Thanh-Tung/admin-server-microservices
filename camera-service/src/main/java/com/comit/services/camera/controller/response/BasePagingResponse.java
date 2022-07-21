package com.comit.services.camera.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasePagingResponse extends BaseResponse {
    @JsonProperty(value = "current_page")
    protected int currentPage;

    @JsonProperty(value = "total_items")
    protected long totalItems;

    @JsonProperty(value = "total_pages")
    protected int totalPages;
}
