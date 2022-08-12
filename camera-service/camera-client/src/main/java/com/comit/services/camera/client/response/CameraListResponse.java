package com.comit.services.camera.client.response;

import com.comit.services.camera.client.dto.CameraDto;
import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.location.client.response.LocationListResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
public class CameraListResponse extends BasePagingResponse {
    @JsonProperty("cameras")
    private List<CameraDto> cameraDtos;

    public CameraListResponse(
            int errorCode,
            String errorMessage,
            List<CameraDto> cameraDtos,
            int currentPage,
            long totalItems,
            int totalPages) {
        this.code = errorCode;
        this.message = errorMessage;
        this.cameraDtos = cameraDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public static CameraListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int currentPage = jsonObject.get("current_page").getAsInt();
            int totalPages = jsonObject.get("total_pages").getAsInt();
            long totalItems = jsonObject.get("total_items").getAsLong();
            List<CameraDto> cameraDtos = new ArrayList<>();
            if (jsonObject.get("cameras").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("cameras").getAsJsonArray();
                jsonArray.forEach(item -> {
                    cameraDtos.add(CameraDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            return new CameraListResponse(code, message, cameraDtos, currentPage, totalItems, totalPages);
        } catch (Exception e) {
            log.error("Error CameraListResponse: " + e.getMessage());
            return null;
        }
    }
}
