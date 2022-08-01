package com.comit.services.feature.model.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FeatureDto extends BaseModelDto {
    private String name;
    private String description;

    @JsonProperty(value = "number_account")
    private int numberAccount;

    @JsonProperty(value = "number_organization")
    private int numberOrganization;
}
