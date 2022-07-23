package com.comit.services.metadata.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MetadataRequest {
    @JsonProperty("image_path")
    private String imagePath;
}
