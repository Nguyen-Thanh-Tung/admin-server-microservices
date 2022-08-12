package com.comit.services.metadata.client.dto;

import com.comit.services.metadata.client.helper.Helper;
import com.google.gson.JsonObject;
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
public class MetadataDto {
    private Integer id;
    private String md5;
    private String path;
    private String type;

    public static MetadataDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject, "id") ? null : jsonObject.get("id").getAsInt();
            String md5 = Helper.isNull(jsonObject, "md5") ? null : jsonObject.get("md5").getAsString();
            String path = Helper.isNull(jsonObject, "path") ? null : jsonObject.get("path").getAsString();
            String type = jsonObject.get("type").isJsonNull() ? null : jsonObject.get("type").getAsString();
            return new MetadataDto(id, md5, path, type);
        } catch (Exception e) {
            log.error("Error MetadataDto: " + e.getMessage());
            return null;
        }
    }
}


