package com.comit.services.employee.client.response;

import com.comit.services.employee.client.data.AreaEmployeeTimeDto;
import com.comit.services.employee.controller.response.BasePagingResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
        this.code = code;
        this.message = message;
        this.areaEmployeeTimes = areaEmployeeTimes;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
