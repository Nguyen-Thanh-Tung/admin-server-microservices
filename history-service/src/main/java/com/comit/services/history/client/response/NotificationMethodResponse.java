package com.comit.services.history.client.response;

import com.comit.services.history.controller.response.BaseResponse;
import com.comit.services.history.model.entity.NotificationMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMethodResponse extends BaseResponse {
    @JsonProperty("notification_method")
    private NotificationMethod notificationMethod;

    public NotificationMethodResponse(int code, String message, NotificationMethod notificationMethod) {
        this.code = code;
        this.message = message;
        this.notificationMethod = notificationMethod;
    }
}
