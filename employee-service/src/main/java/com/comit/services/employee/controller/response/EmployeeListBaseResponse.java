package com.comit.services.employee.controller.response;

import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.model.dto.BaseEmployeeDto;
import com.comit.services.employee.model.dto.EmployeeDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeListBaseResponse extends BasePagingResponse {
    @JsonProperty(value = "employees")
    List<BaseEmployeeDto> employeeDtos;

    public EmployeeListBaseResponse(
            EmployeeErrorCode errorCode,
            List<BaseEmployeeDto> employeeDtos,
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
