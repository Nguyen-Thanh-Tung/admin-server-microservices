package com.comit.services.metadata.server.business;

import com.comit.services.metadata.client.dto.MetadataDto;
import com.comit.services.metadata.client.request.MetadataRequest;
import org.springframework.web.multipart.MultipartFile;

public interface MetadataBusiness {
    MetadataDto saveMetadata(MultipartFile file);

    MetadataDto saveMetadata(MetadataRequest metadataRequest);

    MetadataDto getMetadata(int id);
}
