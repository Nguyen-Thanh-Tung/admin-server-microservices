package com.comit.services.areaRestriction.client;

import com.comit.services.areaRestriction.client.response.EmployeeResponse;
import com.comit.services.areaRestriction.client.response.LocationResponse;
import org.aspectj.weaver.ast.Not;
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
