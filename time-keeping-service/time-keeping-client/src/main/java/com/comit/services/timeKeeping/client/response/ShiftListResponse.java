package com.comit.services.timeKeeping.client.response;

import com.comit.services.timeKeeping.client.dto.ShiftDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ShiftListResponse extends BaseResponse {
    @JsonProperty("shifts")
    private List<ShiftDto> shiftDtos;

    public ShiftListResponse(int code, String message, List<ShiftDto> shiftDtos) {
        this.setCode(code);
        this.setMessage(message);
        this.shiftDtos = shiftDtos;
    }

    public static ShiftListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            List<ShiftDto> shiftDtos = new ArrayList<>();
            if (jsonObject.get("shifts").isJsonArray()) {
                jsonObject.get("shifts").getAsJsonArray().forEach(item -> {
                    shiftDtos.add(ShiftDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            return new ShiftListResponse(code, message, shiftDtos);
        } catch (Exception e) {
            log.error("Error ShiftListResponse: " + e.getMessage());
            return null;
        }
    }
}
