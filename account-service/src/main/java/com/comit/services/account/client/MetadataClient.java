package com.comit.services.account.client;

import com.comit.services.account.client.response.MetadataResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "metadata-service")
public interface MetadataClient {

    @PostMapping("/metadatas/save-file")
    ResponseEntity<MetadataResponseClient> saveMetadata(@RequestHeader String token, MultipartFile file);

    @GetMapping("/metadatas/{id}")
    ResponseEntity<MetadataResponseClient> getMetadata(@RequestHeader String token, @PathVariable Integer id);

}
