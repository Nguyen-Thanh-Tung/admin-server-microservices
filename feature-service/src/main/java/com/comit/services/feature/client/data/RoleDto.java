package com.comit.services.feature.client.data;

import com.comit.services.feature.model.dto.BaseModelDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto extends BaseModelDto {
    private String name;
}
