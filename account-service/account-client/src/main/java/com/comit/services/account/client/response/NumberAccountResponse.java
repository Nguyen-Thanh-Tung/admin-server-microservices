package com.comit.services.account.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class NumberAccountResponse extends BaseResponse {
    @JsonProperty(value = "number_account")
    private int numberAccount;

    public NumberAccountResponse(int errorCode, String errorMessage, int numberAccount) {
        this.code = errorCode;
        this.message = errorMessage;
        this.numberAccount = numberAccount;
    }

    public static NumberAccountResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            int numberAccount = jsonObject.get("number_account").getAsInt();
            return new NumberAccountResponse(code, message, numberAccount);
        } catch (Exception e) {
            log.error("Error NumberAccountResponse: " + e.getMessage());
            return null;
        }
    }
}
