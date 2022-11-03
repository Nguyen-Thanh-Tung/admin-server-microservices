package com.comit.services.employee.model.dto;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseEmployeeDto extends BaseModelDto {
    private String code;
    private String name;

    private String email;
    private String phone;
    private String status;

    @JsonProperty("location_id")
    private Integer locationId;

    @JsonProperty("embedding_id")
    private Integer embeddingId;

    @JsonProperty("manager")
    @JsonIncludeProperties(value = {"id", "code", "name", "image", "email", "phone", "status"})
    private BaseEmployeeDto manager;

    @JsonProperty("image_id")
    private Integer imageId;

    @JsonProperty("shift_ids")
    private String shiftIds;
}
