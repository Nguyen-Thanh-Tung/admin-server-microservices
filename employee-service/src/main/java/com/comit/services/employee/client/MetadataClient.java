package com.comit.services.employee.client;

import com.comit.services.employee.client.request.MetadataRequest;
import com.comit.services.employee.client.response.MetadataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "metadata-service")
public interface MetadataClient {
    @PostMapping("/metadatas/save-path")
    ResponseEntity<MetadataResponse> saveMetadata(@RequestHeader String token, @RequestBody MetadataRequest metadataRequest);

    @GetMapping("/metadatas/{id}")
    ResponseEntity<MetadataResponse> getMetadata(@RequestHeader String token, @PathVariable Integer id);
}
