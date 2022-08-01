package com.comit.services.location.client;

import com.comit.services.location.client.response.EmployeeListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "employee-service")
public interface EmployeeClient {
    @GetMapping("/employees/location/{locationId}")
    ResponseEntity<EmployeeListResponse> getEmployeeOfLocation(@RequestHeader("token") String token, @PathVariable Integer locationId);
}
