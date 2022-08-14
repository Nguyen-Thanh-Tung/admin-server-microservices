package com.comit.services.areaRestriction.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {
    private Integer id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private String status;
    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("embedding_id")
    private Integer embeddingId;


}
