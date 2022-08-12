package com.comit.services.metadata.server.business;

import com.comit.services.metadata.client.dto.MetadataDto;
import com.comit.services.metadata.client.request.MetadataRequest;
import com.comit.services.metadata.server.model.Metadata;
import com.comit.services.metadata.server.service.MetadataServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MetadataBusinessImpl implements MetadataBusiness {
    @Autowired
    private MetadataServices metadataServices;

    @Override
    public MetadataDto saveMetadata(MultipartFile file) {
        Metadata metadata = metadataServices.saveMetadata(file);
        if (metadata == null) {
            return null;
        }
        return convertMetadataToMetadataDto(metadata);
    }

    @Override
    public MetadataDto saveMetadata(MetadataRequest metadataRequest) {
        Metadata metadata = metadataServices.saveMetadata(metadataRequest.getImagePath(), null, "png");
        if (metadata == null) {
            return null;
        }
        return convertMetadataToMetadataDto(metadata);
    }

    @Override
    public MetadataDto getMetadata(int id) {
        Metadata metadata = metadataServices.getMetadata(id);
        if (metadata == null) {
            return null;
        }
        return convertMetadataToMetadataDto(metadata);
    }

    public MetadataDto convertMetadataToMetadataDto(Metadata metadata) {
        if (metadata == null) return null;
        try {
            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(metadata, MetadataDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}
