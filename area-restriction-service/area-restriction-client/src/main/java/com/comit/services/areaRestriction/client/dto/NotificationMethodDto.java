package com.comit.services.areaRestriction.client.dto;

import com.comit.services.areaRestriction.client.helper.Helper;
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
public class NotificationMethodDto {
    private Integer id;
    @JsonProperty(value = "use_ott")
    private boolean useOTT;

    @JsonProperty(value = "use_email")
    private boolean useEmail;

    @JsonProperty(value = "use_screen")
    private boolean useScreen;

    @JsonProperty(value = "use_ring")
    private boolean useRing;

    @JsonProperty(value = "area_restriction_id")
    private Integer areaRestrictionId;

    public static NotificationMethodDto convertJsonToObject(JsonObject jsonObject) {
        try {
            Integer id = Helper.isNull(jsonObject,"id") ? null : jsonObject.get("id").getAsInt();
            boolean useOTT = !Helper.isNull(jsonObject,"use_ott") && jsonObject.get("use_ott").getAsBoolean();
            boolean useEmail = !Helper.isNull(jsonObject,"use_email") && jsonObject.get("use_email").getAsBoolean();
            boolean useScreen = !Helper.isNull(jsonObject,"use_screen") && jsonObject.get("use_screen").getAsBoolean();
            boolean useRing = !Helper.isNull(jsonObject,"use_ring") && jsonObject.get("use_ring").getAsBoolean();
            Integer areaRestrictionId = Helper.isNull(jsonObject,"area_restriction_id") ? null : jsonObject.get("area_restriction_id").getAsInt();
            return new NotificationMethodDto(id, useOTT, useEmail, useScreen, useRing, areaRestrictionId);
        } catch (Exception e) {
            log.error("Error NotificationMethodDto AreaRestriction: " + e.getMessage());
            return null;
        }
    }
}
