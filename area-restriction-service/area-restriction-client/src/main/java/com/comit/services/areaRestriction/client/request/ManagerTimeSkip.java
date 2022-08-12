package com.comit.services.areaRestriction.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ManagerTimeSkip {
    @JsonProperty(value = "manager_id")
    private Integer managerId;

    @JsonProperty(value = "time_skip")
    private Integer timeSkip;

    ManagerTimeSkip(Integer managerId, Integer timeSkip) {
        this.managerId = managerId;
        this.timeSkip = timeSkip;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getTimeSkip() {
        return timeSkip;
    }

    public void setTimeSkip(Integer timeSkip) {
        this.timeSkip = timeSkip;
    }
}