package com.comit.services.userlog.client.response;

import com.comit.services.location.client.dto.LocationDto;
import com.comit.services.location.client.response.LocationListResponse;
import com.comit.services.userlog.client.dto.UserLogDto;
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
public class UserLogListResponse extends BasePagingResponse {
    @JsonProperty(value = "user_logs")
    private List<UserLogDto> userLogDtos;

    public UserLogListResponse(
            int errorCode,
            String errorMessage,
            List<UserLogDto> userLogDtos,
            int currentPage,
            long totalItems,
            int totalPages) {
        super();
        this.setCode(errorCode);
        this.setMessage(errorMessage);
        this.userLogDtos = userLogDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public static UserLogListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int currentPage = jsonObject.get("current_page").getAsInt();
            int totalPages = jsonObject.get("total_pages").getAsInt();
            long totalItems = jsonObject.get("total_items").getAsLong();
            List<UserLogDto> userLogDtos = new ArrayList<>();
            if (jsonObject.get("user_logs").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("user_logs").getAsJsonArray();
                jsonArray.forEach(item -> {
                    userLogDtos.add(UserLogDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            return new UserLogListResponse(code, message, userLogDtos, currentPage, totalItems, totalPages);
        } catch (Exception e) {
            log.error("Error UserLogListResponse: " + e.getMessage());
            return null;
        }
    }
}
