package com.comit.location.client.response;

import com.comit.location.controller.response.BasePagingResponse;
import com.comit.location.model.entity.Camera;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CameraListResponse extends BasePagingResponse {
    @JsonProperty("cameras")
    private List<Camera> cameras;


    public CameraListResponse(
            int code,
            String message,
            List<Camera> cameras,
            int currentPage,
            long totalItems,
            int totalPages) {
        this.code = code;
        this.message = message;
        this.cameras = cameras;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
