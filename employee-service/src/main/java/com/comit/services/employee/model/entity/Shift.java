package com.comit.services.employee.model.entity;

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
    private String timeStart;
    private String timeEnd;
}
