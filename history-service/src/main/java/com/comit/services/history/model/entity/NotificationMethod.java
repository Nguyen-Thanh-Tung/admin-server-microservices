package com.comit.services.history.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMethod {
    private Integer id;
    private boolean useOTT;
    private boolean useEmail;
    private boolean useScreen;
    private boolean useRing;
}
