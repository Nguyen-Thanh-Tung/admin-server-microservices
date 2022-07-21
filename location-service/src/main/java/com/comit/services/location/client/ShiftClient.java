package com.comit.services.location.client;

import com.comit.services.location.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "shift-service")
public interface ShiftClient {
	@PostMapping("/shifts/location/{locationId}")
	ResponseEntity<BaseResponse> addShiftsForLocation(@PathVariable Integer locationId);

	@DeleteMapping("/shifts/location/{locationId}")
	ResponseEntity<BaseResponse> deleteShiftsOfLocation(@PathVariable Integer locationId);
}
