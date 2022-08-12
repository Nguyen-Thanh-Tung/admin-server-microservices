package com.comit.services.metadata.server.service;

import com.comit.services.metadata.server.model.Metadata;
import org.springframework.web.multipart.MultipartFile;

public interface MetadataServices {
    boolean existMetadataByMetadataId(String metadataId);

    Metadata saveMetadata(MultipartFile inFile);

    Metadata saveMetadata(String path, String md5, String type);

    Metadata getMetadata(int id);
}
