package com.comit.services.location.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Camera {
    private String name;
    private String ipAddress;
    private String streamState;
    private String taken;
    private String useCase;
    private String type;
    private String polygons;
    private boolean isUpdated;
}
