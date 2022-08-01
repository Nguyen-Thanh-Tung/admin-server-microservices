package com.comit.services.account.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FeatureRequest {
    private String name;
    private String description;
}
