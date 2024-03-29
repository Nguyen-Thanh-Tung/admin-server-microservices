package com.comit.services.employee.client;

import com.comit.services.employee.client.response.ShiftResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "time-keeping-service")
public interface TimeKeepingClient {

    @GetMapping("/shifts/{id}")
    ResponseEntity<ShiftResponseClient> getShift(@RequestHeader String token, @PathVariable("id") int shiftId);
}
