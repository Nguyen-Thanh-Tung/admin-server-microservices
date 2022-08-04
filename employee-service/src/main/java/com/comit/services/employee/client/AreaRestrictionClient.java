package com.comit.services.employee.client;

import com.comit.services.employee.client.request.AreaEmployeeTimeListRequest;
import com.comit.services.employee.client.response.AreaEmployeeTimeListResponse;
import com.comit.services.employee.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "area-restriction-service")
public interface AreaRestrictionClient {
    @DeleteMapping("/area-restrictions/manager/{managerId}")
    ResponseEntity<BaseResponse> deleteManagerOnAllAreaRestriction(@RequestHeader String token, @PathVariable Integer managerId);

    @DeleteMapping("/area-restrictions/manager/{employeeId}/notification-manager")
    ResponseEntity<BaseResponse> deleteAreaRestrictionManagerNotificationList(@RequestHeader String token, @PathVariable Integer employeeId);

    @PostMapping("/area-restrictions/area-employee-times")
    ResponseEntity<AreaEmployeeTimeListResponse> saveAreaEmployeeTimeList(@RequestHeader String token, @RequestBody AreaEmployeeTimeListRequest areaEmployeeTimeListRequest);

    @DeleteMapping("/area-restrictions/area-employee-times/employee/{employeeId}")
    ResponseEntity<BaseResponse> deleteAreaEmployeeTimeList(@RequestHeader String token, @PathVariable Integer employeeId);

    @GetMapping("/area-restrictions/area-employee-times/employee/{employeeId}")
    ResponseEntity<AreaEmployeeTimeListResponse> getAreaEmployeeTimesOfEmployee(@RequestHeader String token, @PathVariable int employeeId);
}
