package com.comit.services.employee.client.response;

import com.comit.services.employee.client.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EmployeeListResponse extends BasePagingResponse {
    @JsonProperty(value = "employees")
    List<EmployeeDto> employeeDtos;

    public EmployeeListResponse(
            int errorCode,
            String errorMessage,
            List<EmployeeDto> employeeDtos,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.code = errorCode;
        this.message = errorMessage;
        this.employeeDtos = employeeDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }


    public static EmployeeListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int currentPage = jsonObject.get("current_page").getAsInt();
            int totalPages = jsonObject.get("total_pages").getAsInt();
            long totalItems = jsonObject.get("total_items").getAsLong();
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            jsonObject.get("users").getAsJsonArray().forEach(item -> {
                employeeDtos.add(EmployeeDto.convertJsonToObject(item.getAsJsonObject()));
            });
            return new EmployeeListResponse(code, message, employeeDtos, currentPage, totalItems, totalPages);
        } catch (Exception e) {
            log.error("Error EmployeeListResponse: " + e.getMessage());
            return null;
        }
    }
}
