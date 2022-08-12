package com.comit.services.metadata.client.response;
import com.comit.services.metadata.client.dto.MetadataDto;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MetadataResponse {
    private Integer code;
    private String message;
    @JsonProperty(value = "metadata")
    private MetadataDto metadata;

    public static MetadataResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            MetadataDto metadataDto = MetadataDto.convertJsonToObject(jsonObject.get("metadata").getAsJsonObject());
            return new MetadataResponse(code, message, metadataDto);
        } catch (Exception e) {
            log.error("Error MetadataResponse: " + e.getMessage());
            return null;
        }
    }
}
