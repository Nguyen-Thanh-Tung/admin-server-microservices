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

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class OrganizationListResponse extends BaseResponse {
    @JsonProperty(value = "organizations")
    private List<OrganizationDto> organizationDtos;

    public OrganizationListResponse(int errorCode, String errorMessage, List<OrganizationDto> organizationDtos) {
        this.code = errorCode;
        this.message = errorMessage;
        this.organizationDtos = organizationDtos;
    }


    public static OrganizationListResponse convertJsonToObject(String json) {
        try {
            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
            int code = jsonObject.get("code").getAsInt();
            String message = jsonObject.get("message").getAsString();
            List<OrganizationDto> organizationDtos = new ArrayList<>();
            if (jsonObject.get("organizations").isJsonArray()) {
                jsonObject.get("organizations").getAsJsonArray().forEach(item -> {
                    organizationDtos.add(OrganizationDto.convertJsonToObject(item.getAsJsonObject()));
                });
            }
            return new OrganizationListResponse(code, message, organizationDtos);
        } catch (Exception e) {
            log.error("Error OrganizationListResponse: " + e.getMessage());
            return null;
        }
    }
}
