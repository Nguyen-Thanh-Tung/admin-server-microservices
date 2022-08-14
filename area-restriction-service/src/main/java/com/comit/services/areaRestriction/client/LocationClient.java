package com.comit.services.areaRestriction.client;

import com.comit.services.areaRestriction.client.response.LocationResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "location-service")
public interface LocationClient {

    @GetMapping("/locations/{id}")
    ResponseEntity<LocationResponseClient> getLocationById(@RequestHeader String token, @PathVariable("id") Integer locationId);
}
