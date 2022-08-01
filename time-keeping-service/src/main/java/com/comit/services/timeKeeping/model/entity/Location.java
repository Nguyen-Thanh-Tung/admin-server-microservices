package com.comit.services.timeKeeping.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private Integer id;
    private String name;
    private String code;
    private String type;
    private Integer organizationId;
}
