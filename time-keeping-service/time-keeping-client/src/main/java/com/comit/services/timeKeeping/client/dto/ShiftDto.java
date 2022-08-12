package com.comit.services.timeKeeping.client.dto;

import com.comit.services.timeKeeping.client.helper.Helper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
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
public class ShiftDto {
    private Integer id;
    private String name;

    @JsonProperty(value = "time_start")
    private String timeStart;

    @JsonProperty(value = "time_end")
    private String timeEnd;

    public static ShiftDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            String name = Helper.isNull(jsonObject, "name") ? null : jsonObject.get("name").getAsString();
            String timeStart = Helper.isNull(jsonObject, "time_start") ? null : jsonObject.get("time_start").getAsString();
            String timeEnd = Helper.isNull(jsonObject, "time_end") ? null : jsonObject.get("time_end").getAsString();
            return new ShiftDto(id, name, timeStart, timeEnd);
        } catch (Exception e) {
            log.error("Error ShiftDto: " + e.getMessage());
            return null;
        }
    }
}

