package com.comit.services.employee.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shift {
    private Integer id;
    private String name;
    @JsonProperty("time_start")
    private String timeStart;
    @JsonProperty("time_end")
    private String timeEnd;
}
