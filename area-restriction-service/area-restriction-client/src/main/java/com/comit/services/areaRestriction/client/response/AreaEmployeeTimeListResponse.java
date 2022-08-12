package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.client.dto.AreaEmployeeTimeDto;
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
public class AreaEmployeeTimeListResponse extends BasePagingResponse {
    @JsonProperty(value = "area_employee_times")
    private List<AreaEmployeeTimeDto> areaEmployeeTimes;

    public AreaEmployeeTimeListResponse(
            int code,
            String message,
            List<AreaEmployeeTimeDto> areaEmployeeTimes,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.setCode(code);
        this.setMessage(message);
        this.areaEmployeeTimes = areaEmployeeTimes;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }

    public AreaEmployeeTimeListResponse(
            int code,
            String message,
            List<AreaEmployeeTimeDto> areaEmployeeTimes
    ) {
        this.setCode(code);
        this.setMessage(message);
        this.areaEmployeeTimes = areaEmployeeTimes;
    }

    public static AreaEmployeeTimeListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int currentPage = jsonObject.get("current_page").getAsInt();
            int totalPages = jsonObject.get("total_pages").getAsInt();
            long totalItems = jsonObject.get("total_items").getAsLong();
            List<AreaEmployeeTimeDto> areaEmployeeTimeDtos = new ArrayList<>();
            if (jsonObject.get("area_employee_times").isJsonArray()) {
                JsonArray jsonArray = jsonObject.get("area_employee_times").getAsJsonArray();
                jsonArray.forEach(item -> {
                    areaEmployeeTimeDtos.add(AreaEmployeeTimeDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            return new AreaEmployeeTimeListResponse(code, message, areaEmployeeTimeDtos, currentPage, totalItems, totalPages);
        } catch (Exception e) {
            log.error("Error AreaEmployeeTimeListResponse: " + e.getMessage());
            return null;
        }
    }
}
