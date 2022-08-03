package com.comit.services.history.client;

import com.comit.services.history.client.response.MetadataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("metadata-service")
public interface MetadataClient {
    @GetMapping("/metadatas/{imageId}")
    ResponseEntity<MetadataResponse> getMetadata(@RequestHeader String token, @PathVariable Integer imageId);
}
