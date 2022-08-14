package com.comit.services.history.client;

import com.comit.services.history.client.response.AreaRestrictionResponse;
import com.comit.services.history.client.response.NotificationMethodResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "area-restriction-service")
public interface AreaRestrictionClient {

    @GetMapping("/area-restrictions/{id}")
    ResponseEntity<AreaRestrictionResponse> getAreaRestriction(@RequestHeader String token, @PathVariable Integer id);

    @GetMapping("/area-restrictions/{id}/notification-method")
    ResponseEntity<NotificationMethodResponse> getNotificationMethodOfAreaRestriction(@RequestHeader String token, @PathVariable("id") Integer areaRestrictionId);
}
