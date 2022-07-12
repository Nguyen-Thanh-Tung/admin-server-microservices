package com.comit.services.account.model.entity;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organization {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
}
