package com.comit.services.areaRestriction.client.data;

import com.comit.services.areaRestriction.model.dto.BaseModelDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto extends BaseModelDto {
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
