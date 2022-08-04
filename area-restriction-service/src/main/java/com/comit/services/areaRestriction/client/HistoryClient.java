package com.comit.services.areaRestriction.client;

import com.comit.services.areaRestriction.client.response.CountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("history-service")
public interface HistoryClient {

    @GetMapping("/notification-histories/area-restriction/{areaRestrictionId}")
    ResponseEntity<CountResponse> getNumberNotificationOfAreaRestriction(@RequestHeader String token, @PathVariable int areaRestrictionId);
}
