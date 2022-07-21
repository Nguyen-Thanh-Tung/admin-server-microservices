package com.comit.services.employee.client;

import com.comit.services.employee.client.request.AreaEmployeeTimeListRequest;
import com.comit.services.employee.client.response.AreaEmployeeTimeListResponse;
import com.comit.services.employee.client.response.AreaRestrictionListResponse;
import com.comit.services.employee.client.response.LocationResponse;
import com.comit.services.employee.controller.response.BaseResponse;
import com.comit.services.employee.model.entity.AreaRestriction;
import org.aspectj.weaver.ast.Not;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "area-restriction-service")
public interface AreaRestrictionClient {
    @GetMapping("/areaRestrictions/manager/{managerId}")
    ResponseEntity<AreaRestrictionListResponse> getAreaRestrictions(@PathVariable Integer managerId);

    @GetMapping("/areaRestrictions/notificationManager/{employeeId}")
    ResponseEntity<BaseResponse> deleteAreaRestrictionManagerNotificationList(@PathVariable Integer employeeId);

    @PostMapping("/areaRestriction/areaEmployeeTime")
    ResponseEntity<AreaEmployeeTimeListResponse> saveAreaEmployeeTimeList(@RequestBody AreaEmployeeTimeListRequest areaEmployeeTimeListRequest);

    @DeleteMapping("/areaRestriction/areaEmployeeTime/employee/{employeeId}")
    ResponseEntity<BaseResponse> deleteAreaEmployeeTimeList(@PathVariable Integer employeeId);
}
