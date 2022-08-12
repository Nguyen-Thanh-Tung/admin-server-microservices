package com.comit.services.areaRestriction.client.response;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BaseResponse {
    private Integer code;
    private String message;

    public static BaseResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            return new BaseResponse(code, message);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
