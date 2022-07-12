package com.comit.location.client;

import com.comit.location.client.response.EmployeeListResponse;
import com.comit.location.model.entity.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "employee-service")
public interface EmployeeClient {
	@GetMapping("/employees/location/{locationId}")
	ResponseEntity<EmployeeListResponse> getEmployeeOfLocation(@PathVariable(name = "locationId") int locationId);
}
