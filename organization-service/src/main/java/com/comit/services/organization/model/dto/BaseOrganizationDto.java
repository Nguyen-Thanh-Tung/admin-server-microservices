package com.comit.services.organization.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseOrganizationDto extends BaseModelDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
}
