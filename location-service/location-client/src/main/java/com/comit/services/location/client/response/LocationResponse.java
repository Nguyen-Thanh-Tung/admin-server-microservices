package com.comit.services.location.client.response;


import com.comit.services.location.client.dto.LocationDto;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class LocationResponse extends BaseResponse{
    private LocationDto location;

    public LocationResponse(int code, String message, LocationDto location) {
        this.code = code;
        this.message = message;
        this.location = location;
    }

    public static LocationResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            LocationDto locationDto = LocationDto.convertJsonToObject(jsonObject.get("location").getAsJsonObject());
            return new LocationResponse(code, message, locationDto);
        } catch (Exception e) {
            log.error("Error LocationResponse: " + e.getMessage());
            return null;
        }
    }
}

