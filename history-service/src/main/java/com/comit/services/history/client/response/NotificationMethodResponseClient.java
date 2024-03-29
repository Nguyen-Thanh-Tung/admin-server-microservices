package com.comit.services.history.client.response;

import com.comit.services.history.client.data.NotificationMethodDtoClient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationMethodResponseClient extends BaseResponseClient {
    @JsonProperty("notification_method")
    private NotificationMethodDtoClient notificationMethod;

    public NotificationMethodResponseClient(int code, String message, NotificationMethodDtoClient notificationMethod) {
        this.code = code;
        this.message = message;
        this.notificationMethod = notificationMethod;
    }
}
