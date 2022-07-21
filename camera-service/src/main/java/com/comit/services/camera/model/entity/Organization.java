package com.comit.services.camera.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
