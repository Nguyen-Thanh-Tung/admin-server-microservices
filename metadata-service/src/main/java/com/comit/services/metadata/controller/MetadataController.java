package com.comit.services.metadata.controller;

import com.comit.services.metadata.business.MetadataBusiness;
import com.comit.services.metadata.constant.MetadataErrorCode;
import com.comit.services.metadata.controller.request.MetadataRequest;
import com.comit.services.metadata.controller.response.MetadataResponse;
import com.comit.services.metadata.model.dto.MetadataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/metadatas")
public class MetadataController {
    @Autowired
    private MetadataBusiness metadataBusiness;

    @PostMapping("/save-file")
    ResponseEntity<MetadataResponse> saveMetadataWithFile(MultipartFile file) {
        MetadataDto metadataDto = metadataBusiness.saveMetadata(file);
        return new ResponseEntity<>(new MetadataResponse(metadataDto == null ? MetadataErrorCode.SUCCESS : MetadataErrorCode.FAIL, metadataDto), HttpStatus.OK);
    }

    @PostMapping("/save-path")
    ResponseEntity<MetadataResponse> saveMetadataWithPath(@RequestBody MetadataRequest metadataRequest) {
        MetadataDto metadataDto = metadataBusiness.saveMetadata(metadataRequest);
        return new ResponseEntity<>(new MetadataResponse(metadataDto == null ? MetadataErrorCode.SUCCESS : MetadataErrorCode.FAIL, metadataDto), HttpStatus.OK);
    }
}
