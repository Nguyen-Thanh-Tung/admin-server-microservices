package com.comit.services.employee.client.response;

import com.comit.services.employee.client.dto.EmployeeDto;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class EmployeeResponse extends BaseResponse {
    private EmployeeDto employee;

    public EmployeeResponse(int code, String message, EmployeeDto employee) {
        this.code = code;
        this.message = message;
        this.employee = employee;
    }

    public static EmployeeResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            EmployeeDto userDto = EmployeeDto.convertJsonToObject(jsonObject.get("user").getAsJsonObject());
            return new EmployeeResponse(code, message, userDto);
        } catch (Exception e) {
            log.error("Error EmployeeResponse: " + e.getMessage());
            return null;
        }
    }
}
