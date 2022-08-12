package com.comit.services.areaRestriction.server.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "notification_methods")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMethod extends BaseModel {
    @Column(name = "use_ott", columnDefinition = "boolean default false")
    private boolean useOTT;

    @Column(name = "use_email", columnDefinition = "boolean default false")
    private boolean useEmail;

    @Column(name = "use_screen", columnDefinition = "boolean default false")
    private boolean useScreen;

    @Column(name = "use_ring", columnDefinition = "boolean default false")
    private boolean useRing;

    @Column(name = "area_restriction_id")
    private Integer areaRestrictionId;
}
