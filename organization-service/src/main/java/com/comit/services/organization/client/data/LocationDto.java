package com.comit.services.organization.client.data;

import com.comit.services.organization.model.dto.BaseModelDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto extends BaseModelDto {
    private String name;
    private String code;
    private String type;
}
