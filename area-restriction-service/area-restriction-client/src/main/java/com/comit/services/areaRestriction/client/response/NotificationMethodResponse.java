package com.comit.services.areaRestriction.client.response;

import com.comit.services.areaRestriction.client.dto.NotificationMethodDto;
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
public class NotificationMethodResponse extends BaseResponse {
    @JsonProperty("notification_method")
    private NotificationMethodDto notificationMethod;

    public NotificationMethodResponse(int code, String message, NotificationMethodDto notificationMethod) {
        this.setCode(code);
        this.setMessage(message);
        this.notificationMethod = notificationMethod;
    }

    public static NotificationMethodResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            NotificationMethodDto notificationMethodDto = jsonObject.get("notification_method").isJsonNull() ? null : NotificationMethodDto.convertJsonToObject(jsonObject.get("notification_method").getAsJsonObject());
            return new NotificationMethodResponse(code, message, notificationMethodDto);
        } catch (Exception e) {
            log.error("Error NotificationMethodResponse AR: " + e.getMessage());
            return null;
        }
    }
}
