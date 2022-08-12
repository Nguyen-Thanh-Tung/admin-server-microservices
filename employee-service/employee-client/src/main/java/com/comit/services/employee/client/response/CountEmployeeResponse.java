package com.comit.services.employee.client.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class CountEmployeeResponse extends BaseResponse {
    private Integer number;

    public CountEmployeeResponse(int code, String message, Integer number) {
        this.code = code;
        this.message = message;
        this.number = number;
    }

    public static CountEmployeeResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int number = jsonObject.get("number").getAsInt();
            return new CountEmployeeResponse(code, message, number);
        } catch (Exception e) {
            log.error("Error CountEmployeeResponse: " + e.getMessage());
            return null;
        }
    }
}
