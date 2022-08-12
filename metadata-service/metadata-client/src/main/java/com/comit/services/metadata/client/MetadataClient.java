package com.comit.services.metadata.client;

import com.comit.services.metadata.client.request.MetadataRequest;
import com.comit.services.metadata.client.response.MetadataResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MetadataClient {
    MetadataResponse getMetadata(String token, Integer imageId);

    MetadataResponse saveMetadata(String token, MetadataRequest metadataRequest);

    MetadataResponse saveMetadata(String token, MultipartFile file);
}
