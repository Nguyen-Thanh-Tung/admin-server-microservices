package com.comit.services.history.client;

import com.comit.services.history.client.response.LocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "location-service")
public interface LocationClient {

    @GetMapping("/locations/{id}")
    ResponseEntity<LocationResponse> getLocation(@RequestHeader String token, @PathVariable("id") Integer locationId);
}
