package com.comit.location.client;

import com.comit.location.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "shift-service")
public interface ShiftClient {
	@PostMapping("/shifts/location/{locationId}")
	ResponseEntity<BaseResponse> addShiftsForLocation(@PathVariable(name = "locationId") int locationId);

	@DeleteMapping("/shifts/location/{locationId}")
	ResponseEntity<BaseResponse> deleteShiftsOfLocation(@PathVariable(name = "locationId") int locationId);
}
