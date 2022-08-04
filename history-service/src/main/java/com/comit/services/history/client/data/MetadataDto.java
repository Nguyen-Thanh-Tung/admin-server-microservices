package com.comit.services.history.client.data;

import com.comit.services.history.model.dto.BaseModelDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDto extends BaseModelDto {
    private String md5;
    private String path;
    private String type;
}

