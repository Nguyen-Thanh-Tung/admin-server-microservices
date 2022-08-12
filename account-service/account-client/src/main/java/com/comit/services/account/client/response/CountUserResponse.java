package com.comit.services.account.client.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class CountUserResponse extends BaseResponse {
    private int number;

    public CountUserResponse(
            int code,
            String message,
            int number) {
        this.code = code;
        this.message = message;
        this.number = number;
    }

    public static CountUserResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int number = jsonObject.get("number").getAsInt();
            return new CountUserResponse(code, message, number);
        } catch (Exception e) {
            log.error("Error CountUserResponse" + e.getMessage());
            return null;
        }
    }
}
