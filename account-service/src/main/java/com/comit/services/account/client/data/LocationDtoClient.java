package com.comit.services.account.client.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDtoClient {
    private Integer id;
    private String name;
    private String code;
    private String type;
    @JsonProperty(value = "organization_id")
    private Integer organizationId;
}
