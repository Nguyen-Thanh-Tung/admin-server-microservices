package com.comit.services.organization.client.response;

import com.comit.services.organization.client.dto.OrganizationDto;
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
public class OrganizationResponse extends BaseResponse {
    @JsonProperty(value = "organization")
    private OrganizationDto organization;

    public OrganizationResponse(int code, String message, OrganizationDto organization) {
        this.organization = organization;
        this.code = code;
        this.message = message;
    }

    public static OrganizationResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            OrganizationDto organizationDto = OrganizationDto.convertJsonToObject(jsonObject.get("organization").getAsJsonObject());
            return new OrganizationResponse(code, message, organizationDto);
        } catch (Exception e) {
            log.error("Error OrganizationResponse: " + e.getMessage());
            return null;
        }
    }
}

