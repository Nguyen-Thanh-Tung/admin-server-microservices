package com.comit.services.history.client.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class CountNotificationResponse extends BaseResponse {
    private int number;

    public CountNotificationResponse(int errorCode, String errorMessage, int number) {
        this.code = errorCode;
        this.message = errorMessage;
        this.number = number;
    }

    public static CountNotificationResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int number = jsonObject.get("number").getAsInt();
            return new CountNotificationResponse(code, message, number);
        } catch (Exception e) {
            log.error("Error CountNotificationResponse: " + e.getMessage());
            return null;
        }
    }
}

