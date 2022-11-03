package com.comit.services.metadata.business;

import com.comit.services.metadata.controller.request.MetadataRequest;
import com.comit.services.metadata.model.dto.MetadataDto;
import org.springframework.web.multipart.MultipartFile;

public interface MetadataBusiness {
    MetadataDto saveMetadata(MultipartFile file);

    MetadataDto saveMetadata(MetadataRequest metadataRequest);

    MetadataDto getMetadata(int id);
}
