package com.comit.services.metadata.client.helper;

import com.google.gson.JsonObject;

public class Helper {
    public static boolean isNull(JsonObject jsonObject, String field) {
        return !jsonObject.has(field) || jsonObject.get(field).isJsonNull();
    }
}