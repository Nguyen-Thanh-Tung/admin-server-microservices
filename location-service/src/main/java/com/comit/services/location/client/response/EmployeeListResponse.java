package com.comit.services.location.client.response;

import com.comit.services.location.controller.response.BasePagingResponse;
import com.comit.services.location.model.entity.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeListResponse extends BasePagingResponse {
    @JsonProperty(value = "employees")
    List<Employee> employees;

    public EmployeeListResponse(
            int code,
            String message,
            List<Employee> employees,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.code = code;
        this.message = message;
        this.employees = employees;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
