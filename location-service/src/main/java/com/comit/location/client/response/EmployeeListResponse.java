package com.comit.location.client.response;

import com.comit.location.controller.response.BasePagingResponse;
import com.comit.location.model.entity.Employee;
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
