package com.comit.services.areaRestriction.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {
    private Integer id;
    private String name;
    private String code;
    private String email;
    private String phone;
    private String status;
    private Integer embeddingId;
    private Integer locationId;
}
