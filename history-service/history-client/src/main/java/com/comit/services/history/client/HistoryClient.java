package com.comit.services.history.client;

import com.comit.services.history.client.response.CountNotificationResponse;

public interface HistoryClient {
    CountNotificationResponse getNumberNotificationOfAreaRestriction(String token, int areaRestrictionId);
}
