package com.comit.services.history.client.response;

import com.comit.services.history.client.data.NotificationMethodDto;
import com.comit.services.history.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMethodResponse extends BaseResponse {
    @JsonProperty("notification_method")
    private NotificationMethodDto notificationMethod;

    public NotificationMethodResponse(int code, String message, NotificationMethodDto notificationMethod) {
        this.code = code;
        this.message = message;
        this.notificationMethod = notificationMethod;
    }
}
