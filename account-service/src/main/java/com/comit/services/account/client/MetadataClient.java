package com.comit.services.account.client;

import com.comit.services.account.controller.response.MetadataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "metadata-service")
public interface MetadataClient {

	@PostMapping("/metadatas")
	ResponseEntity<MetadataResponse> saveMetadata(MultipartFile file);
}
