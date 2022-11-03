package com.comit.services.history.controller.response;

import com.comit.services.history.constant.HistoryErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaRestrictionCountResponse extends BaseResponse {
    @JsonProperty(value = "number_area_restriction_warning")
    private int numberAreaRestrictionWarning;

    @JsonProperty(value = "number_notification_in_day")
    private int numberNotificationInDay;

    @JsonProperty(value = "number_notification_not_resolve")
    private int numberNotificationNotResolve;

    @JsonProperty(value = "number_notification_not_resolve_using_ring")
    private int numberNotificationNotResolveUsingRing;

    public AreaRestrictionCountResponse(
            HistoryErrorCode errorCode,
            int numberAreaRestrictionWarning,
            int numberNotificationInDay,
            int numberNotificationNotResolve,
            int numberNotificationNotResolveUsingRing
    ) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.numberAreaRestrictionWarning = numberAreaRestrictionWarning;
        this.numberNotificationInDay = numberNotificationInDay;
        this.numberNotificationNotResolve = numberNotificationNotResolve;
        this.numberNotificationNotResolveUsingRing = numberNotificationNotResolveUsingRing;
    }
}
