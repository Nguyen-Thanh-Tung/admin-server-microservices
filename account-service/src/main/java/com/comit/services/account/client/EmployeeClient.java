package com.comit.services.account.client;

import com.comit.services.account.client.response.CountResponseClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "employee-service")
public interface EmployeeClient {
    @GetMapping("/employees/location/{locationId}/number-employee")
    ResponseEntity<CountResponseClient> getNumberEmployeeOfLocation(@RequestHeader String token, @PathVariable Integer locationId);
}
