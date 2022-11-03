package com.comit.services.account.client.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDtoClient {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
}
