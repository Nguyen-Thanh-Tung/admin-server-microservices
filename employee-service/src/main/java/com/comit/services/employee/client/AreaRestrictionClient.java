package com.comit.services.employee.client;

import com.comit.services.employee.client.request.AreaEmployeeTimeListRequestClient;
import com.comit.services.employee.client.response.AreaEmployeeTimeListResponseClient;
import com.comit.services.employee.controller.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "area-restriction-service")
public interface AreaRestrictionClient {
    @DeleteMapping("/area-restrictions/manager/{managerId}")
    ResponseEntity<BaseResponse> deleteManagerOnAllAreaRestriction(@RequestHeader String token, @PathVariable Integer
    managerId, @RequestHeader String internal);

    @DeleteMapping("/area-restrictions/manager/{employeeId}/notification-manager")
    ResponseEntity<BaseResponse> deleteAreaRestrictionManagerNotificationList(@RequestHeader String token, @PathVariable
    Integer employeeId, @RequestHeader String internal);

    @PostMapping("/area-restrictions/area-employee-times")
    ResponseEntity<AreaEmployeeTimeListResponseClient> saveAreaEmployeeTimeList(@RequestHeader String token,
    @RequestBody AreaEmployeeTimeListRequestClient areaEmployeeTimeListRequestClient, @RequestHeader String internal);

    @DeleteMapping("/area-restrictions/area-employee-times/employee/{employeeId}")
    ResponseEntity<BaseResponse> deleteAreaEmployeeTimeList(@RequestHeader String token, @PathVariable Integer employeeId,
    @RequestHeader String internal);

    @GetMapping("/area-restrictions/area-employee-times/employee/{employeeId}")
    ResponseEntity<AreaEmployeeTimeListResponseClient> getAreaEmployeeTimesOfEmployee(@RequestHeader String token,
    @PathVariable int employeeId, @RequestHeader String internal);
}
