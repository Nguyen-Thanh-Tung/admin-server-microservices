package com.comit.services.history.model.dto;

import com.comit.services.history.model.entity.Metadata;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDto extends BaseModelDto {
    private String md5;
    private String path;
    private String type;

    public static MetadataDto convertMetadataToMetadataDto(Metadata metadata) {
        if (metadata == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(metadata, MetadataDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}

