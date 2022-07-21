package com.comit.services.camera.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaRestriction {
    private Integer id;
    private String name;
    private String code;
    private String timeStart;
    private String timeEnd;
    private Integer locationId;
}
