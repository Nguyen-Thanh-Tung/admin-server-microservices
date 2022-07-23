package com.comit.services.areaRestriction.client;

import com.comit.services.areaRestriction.client.response.LocationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/users/current/location")
    ResponseEntity<LocationResponse> getLocationOfCurrentUser(@RequestHeader String token);
}
