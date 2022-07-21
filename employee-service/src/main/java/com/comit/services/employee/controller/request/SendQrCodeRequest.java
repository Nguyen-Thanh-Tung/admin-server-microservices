package com.comit.services.employee.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SendQrCodeRequest {
    @JsonProperty(value = "employee_ids")
    private List<Integer> employeeIds;

    @JsonProperty(value = "check_all")
    private Boolean checkAll;
}
