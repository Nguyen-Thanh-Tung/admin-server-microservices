package com.comit.services.employee.controller.response;

import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.model.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EmployeeListResponse extends BasePagingResponse {
    @JsonProperty(value = "employees")
    List<EmployeeDto> employeeDtos;

    public EmployeeListResponse(
            EmployeeErrorCode errorCode,
            List<EmployeeDto> employeeDtos,
            int currentPage,
            long totalItems,
            int totalPages
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.employeeDtos = employeeDtos;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
