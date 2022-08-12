package com.comit.services.userlog.client.dto;

import com.comit.services.account.client.dto.UserDto;
import com.comit.services.userlog.client.helper.Helper;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class UserLogDto {
    private Integer id;
    private String content;
    private Date time;
    @JsonIncludeProperties({"id", "username", "fullname"})
    private UserDto user;

    public static UserLogDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            String content = Helper.isNull(jsonObject, "content") ? null : jsonObject.get("content").getAsString();
            Date time = Helper.isNull(jsonObject, "time") ? null : new Date(jsonObject.get("time").getAsString());
            UserDto userDto = Helper.isNull(jsonObject, "user") ? null : UserDto.convertJsonToObject(jsonObject.get("user").getAsJsonObject());
            return new UserLogDto(id, content, time, userDto);
        } catch (Exception e) {
            log.error("Error UserLogDto: " + e.getMessage());
            return null;
        }
    }
}
