package com.comit.services.camera.controller.response;

import com.comit.services.camera.constant.CameraErrorCode;
import com.comit.services.camera.model.dto.CameraDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CameraListResponse extends BasePagingResponse {
    @JsonProperty("cameras")
    private List<CameraDto> cameraDtos;


    public CameraListResponse(
            CameraErrorCode errorCode,
            List<CameraDto> cameraDtos,
            int currentPage,
            long totalItems,
            int totalPages) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.cameraDtos = cameraDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
