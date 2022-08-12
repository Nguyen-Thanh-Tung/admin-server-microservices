package com.comit.services.metadata.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MetadataRequest {
    @JsonProperty("image_path")
    private String imagePath;
}

