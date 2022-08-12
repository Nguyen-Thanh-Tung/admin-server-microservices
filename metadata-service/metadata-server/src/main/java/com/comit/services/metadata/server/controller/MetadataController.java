package com.comit.services.metadata.server.controller;

import com.comit.services.metadata.client.dto.MetadataDto;
import com.comit.services.metadata.client.request.MetadataRequest;
import com.comit.services.metadata.client.response.MetadataResponse;
import com.comit.services.metadata.server.business.MetadataBusiness;
import com.comit.services.metadata.server.constant.MetadataErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/metadatas")
public class MetadataController {
    @Autowired
    private MetadataBusiness metadataBusiness;

    @PostMapping("/save-file")
    ResponseEntity<MetadataResponse> saveMetadataWithFile(MultipartFile file) {
        MetadataDto metadataDto = metadataBusiness.saveMetadata(file);
        return new ResponseEntity<>(new MetadataResponse(MetadataErrorCode.SUCCESS.getCode(), MetadataErrorCode.SUCCESS.getMessage(), metadataDto), HttpStatus.OK);
    }

    @PostMapping("/save-path")
    ResponseEntity<MetadataResponse> saveMetadataWithPath(@RequestBody MetadataRequest metadataRequest) {
        MetadataDto metadataDto = metadataBusiness.saveMetadata(metadataRequest);
        return new ResponseEntity<>(new MetadataResponse(MetadataErrorCode.SUCCESS.getCode(), MetadataErrorCode.SUCCESS.getMessage(), metadataDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<MetadataResponse> getMetadata(@PathVariable int id) {
        MetadataDto metadataDto = metadataBusiness.getMetadata(id);
        return new ResponseEntity<>(new MetadataResponse(MetadataErrorCode.SUCCESS.getCode(), MetadataErrorCode.SUCCESS.getMessage(), metadataDto), HttpStatus.OK);

    }
}
