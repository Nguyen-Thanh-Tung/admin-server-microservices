package com.comit.services.account.client.response;

import com.comit.services.account.client.data.OrganizationDtoClient;
import com.comit.services.account.controller.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationResponseClient extends BaseResponseClient {
    @JsonProperty(value = "organization")
    private OrganizationDtoClient organization;

    public OrganizationResponseClient(int code, String message, OrganizationDtoClient organization) {
        this.organization = organization;
        this.code = code;
        this.message = message;
    }

    @Getter
    @Setter
    public static class CountResponseClient extends BaseResponse {
        @JsonProperty("number")
        private int number;

        public CountResponseClient(
                int code,
                String message,
                int number) {
            this.code = code;
            this.message = message;
            this.number = number;
        }
    }
}
