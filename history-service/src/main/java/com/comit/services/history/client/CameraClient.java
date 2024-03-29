package com.comit.services.history.client;

import com.comit.services.history.client.response.CameraResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "camera-service")
public interface CameraClient {
    @GetMapping("/cameras/{id}/base")
    ResponseEntity<CameraResponseClient> getCamera(@RequestHeader String token, @PathVariable Integer id);
}
