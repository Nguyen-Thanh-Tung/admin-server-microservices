package com.comit.services.history.client;

import com.comit.services.history.client.response.EmployeeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "employee-service")
public interface EmployeeClient {
    @GetMapping("/employees/{id}")
    ResponseEntity<EmployeeResponse> getEmployee(@RequestHeader("token") String token, @PathVariable Integer id);
}
