package com.comit.services.areaRestriction.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AreaRestrictionNotificationRequest {
    @JsonProperty(value = "use_ott")
    private Boolean useOTT;

    @JsonProperty(value = "use_email")
    private Boolean useEmail;

    @JsonProperty(value = "use_screen")
    private Boolean useScreen;

    @JsonProperty(value = "use_ring")
    private Boolean useRing;

    // manager_times = [{"manager_id":5,"time_skip":3},{"manager_id":3,"time_skip":5},]
    @JsonProperty(value = "manager_times")
    private List<ManagerTimeSkip> managerTimeSkips;
}

