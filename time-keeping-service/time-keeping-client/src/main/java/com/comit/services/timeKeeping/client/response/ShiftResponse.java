package com.comit.services.timeKeeping.client.response;

import com.comit.services.timeKeeping.client.dto.ShiftDto;
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
public class ShiftResponse extends BaseResponse{
    private Integer code;
    private String message;
    private ShiftDto shift;

    public ShiftResponse(int errorCode, String errorMessage, ShiftDto shift) {
        this.code = errorCode;
        this.message = errorMessage;
        this.shift = shift;
    }

    public static ShiftResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            ShiftDto shiftDto = ShiftDto.convertJsonToObject(jsonObject.get("shift").getAsJsonObject());
            return new ShiftResponse(code, message, shiftDto);
        } catch (Exception e) {
            log.error("Error ShiftResponse: " + e.getMessage());
            return null;
        }
    }
}

