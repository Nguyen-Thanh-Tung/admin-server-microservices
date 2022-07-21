package com.comit.services.account.client.request;

import com.comit.services.account.model.entity.Organization;
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

    public OrganizationRequest(Organization organization) {
        this.name = organization.getName();
        this.email = organization.getEmail();
        this.phone = organization.getPhone();
        this.address = organization.getAddress();
        this.description = organization.getDescription();
    }
}
