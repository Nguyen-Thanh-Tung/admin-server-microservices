package com.comit.services.location.client.response;

import com.comit.services.location.client.dto.LocationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class LocationListResponse extends BasePagingResponse{
    @JsonProperty("locations")
    private List<LocationDto> locations;
    public LocationListResponse(int code, String message, List<LocationDto> locations, int currentPage, long totalItems, int totalPage) {
        this.code = code;
        this.message = message;
        this.locations = locations;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPage;
    }

    public static LocationListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int currentPage = jsonObject.get("current_page").getAsInt();
            int totalPages = jsonObject.get("total_pages").getAsInt();
            long totalItems = jsonObject.get("total_items").getAsLong();
            List<LocationDto> locationDtos = new ArrayList<>();
            if (jsonObject.get("locations").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("locations").getAsJsonArray();
                jsonArray.forEach(item -> {
                    locationDtos.add(LocationDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            return new LocationListResponse(code, message, locationDtos, currentPage, totalItems, totalPages);
        } catch (Exception e) {
            log.error("Error LocationListResponse: " + e.getMessage());
            return null;
        }
    }
}

