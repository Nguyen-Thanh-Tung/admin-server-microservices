package com.comit.services.employee.client;

import com.comit.services.employee.client.request.MetadataRequest;
import com.comit.services.employee.client.response.MetadataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "metadata-service")
public interface MetadataClient {
    @PostMapping("/metadatas/save-path")
    ResponseEntity<MetadataResponse> saveMetadata(@RequestHeader("token") String token, @RequestBody MetadataRequest metadataRequest);
}
