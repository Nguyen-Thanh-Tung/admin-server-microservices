package com.comit.organization.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationRequest {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
}
