package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.client.dto.AreaRestrictionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class AreaRestrictionResponse extends BaseResponse {
    @JsonProperty(value = "area_restriction")
    private AreaRestrictionDto areaRestriction;

    public AreaRestrictionResponse(int code, String message, AreaRestrictionDto areaRestriction) {
        this.setCode(code);
        this.setMessage(message);
        this.areaRestriction = areaRestriction;
    }

    public static AreaRestrictionResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            AreaRestrictionDto areaRestrictionDto = jsonObject.get("area_restriction").isJsonNull() ? null : AreaRestrictionDto.convertJsonToObject(jsonObject.get("area_restriction").getAsJsonObject());
            return new AreaRestrictionResponse(code, message, areaRestrictionDto);
        } catch (Exception e) {
            log.error("Error AreaRestrictionResponse: " + e.getMessage());
            return null;
        }
    }
}
