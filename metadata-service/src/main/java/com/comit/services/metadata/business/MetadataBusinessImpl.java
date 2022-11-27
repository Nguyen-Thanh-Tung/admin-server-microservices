package com.comit.services.metadata.business;

import com.comit.services.metadata.controller.request.MetadataRequest;
import com.comit.services.metadata.model.dto.MetadataDto;
import com.comit.services.metadata.model.entity.Metadata;
import com.comit.services.metadata.service.MetadataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class MetadataBusinessImpl implements MetadataBusiness {
    @Autowired
    private MetadataServices metadataServices;
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Value("${app.internalToken}")
    private String internalToken;

    @Override
    public MetadataDto saveMetadata(MultipartFile file) {
        if (!isInternalFeature()) return null;
        Metadata metadata = metadataServices.saveMetadata(file);
        if (metadata == null) {
            return null;
        }
        return MetadataDto.convertMetadataToMetadataDto(metadata);
    }

    @Override
    public MetadataDto saveMetadata(MetadataRequest metadataRequest) {
        if (!isInternalFeature()) return null;
        Metadata metadata = metadataServices.saveMetadata(metadataRequest.getImagePath(), null, "png");
        if (metadata == null) {
            return null;
        }
        return MetadataDto.convertMetadataToMetadataDto(metadata);
    }

    @Override
    public MetadataDto getMetadata(int id) {
        Metadata metadata = metadataServices.getMetadata(id);
        if (metadata == null) {
            return null;
        }
        return MetadataDto.convertMetadataToMetadataDto(metadata);
    }

    public boolean isInternalFeature() {
        return Objects.equals(httpServletRequest.getHeader("token"), internalToken);
    }
}
