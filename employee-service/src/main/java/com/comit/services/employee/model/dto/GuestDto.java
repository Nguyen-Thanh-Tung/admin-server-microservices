package com.comit.services.employee.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuestDto extends BaseModelDto {
    private String name;
    private String email;
    private String phone;
    @JsonProperty("embedding_id")
    private Integer embeddingId;
    private String image;
    @JsonProperty("location_id")
    private Integer locationId;
    @JsonProperty("area_restriction_id")
    private Integer areaRestrictionId;
    @JsonProperty("time_start")
    private Date timeStart;
    @JsonProperty("time_end")
    private Date timeEnd;
}
