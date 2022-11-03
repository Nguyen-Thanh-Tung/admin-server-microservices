package com.comit.services.employee.client;

import com.comit.services.employee.client.request.MetadataRequestClient;
import com.comit.services.employee.client.response.MetadataResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "metadata-service")
public interface MetadataClient {
    @PostMapping("/metadatas/save-path")
    ResponseEntity<MetadataResponseClient> saveMetadata(@RequestHeader String token, @RequestBody MetadataRequestClient
            metadataRequestClient, @RequestHeader String internal);

    @GetMapping("/metadatas/{id}")
    ResponseEntity<MetadataResponseClient> getMetadata(@RequestHeader String token, @PathVariable Integer id);
}
