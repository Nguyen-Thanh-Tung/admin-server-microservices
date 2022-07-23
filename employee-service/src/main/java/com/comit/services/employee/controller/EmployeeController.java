package com.comit.services.employee.controller;

import com.comit.services.employee.business.EmployeeBusiness;
import com.comit.services.employee.constant.Const;
import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.controller.request.SendQrCodeRequest;
import com.comit.services.employee.controller.response.BaseResponse;
import com.comit.services.employee.controller.response.EmployeeListResponse;
import com.comit.services.employee.controller.response.EmployeeResponse;
import com.comit.services.employee.model.dto.EmployeeDto;
import com.comit.services.employee.model.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    EmployeeBusiness employeeBusiness;

    @GetMapping("")
    public ResponseEntity<BaseResponse> getAllEmployee(
            @RequestParam(defaultValue = Const.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Const.DEFAULT_SIZE_PAGE) int size,
            @RequestParam(required = false, value = "status") String status,
            @RequestParam(required = false) String search

    ) {
        Page<Employee> employeePage = employeeBusiness.getEmployeePage(status, page, size, search);
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        int currentPage = 0;
        long totalItems = 0;
        int totalPages = 0;
        if (employeePage != null) {
            currentPage = employeePage.getNumber();
            totalItems = employeePage.getTotalElements();
            totalPages = employeePage.getTotalPages();
            employeeDtos = employeeBusiness.getAllEmployee(employeePage.getContent());
        }

        return new ResponseEntity<>(new EmployeeListResponse(EmployeeErrorCode.SUCCESS, employeeDtos, currentPage, totalItems, totalPages), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getEmployee(@PathVariable int id) {
        EmployeeDto employeeDto = employeeBusiness.getEmployee(id);
        return new ResponseEntity<>(new EmployeeResponse(EmployeeErrorCode.SUCCESS, employeeDto), HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<BaseResponse> saveEmployee(HttpServletRequest servletRequest) {
        EmployeeDto employeeDto = employeeBusiness.saveEmployee(servletRequest);
        return new ResponseEntity<>(new EmployeeResponse(EmployeeErrorCode.SUCCESS, employeeDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateEmployee(@PathVariable int id, HttpServletRequest servletRequest) {
        EmployeeDto employeeDto = employeeBusiness.updateEmployee(id, servletRequest);
        return new ResponseEntity<>(new EmployeeResponse(EmployeeErrorCode.SUCCESS, employeeDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteEmployee(@PathVariable int id) {
        boolean deleteSuccess = employeeBusiness.deleteEmployee(id);
        return new ResponseEntity<>(new BaseResponse(deleteSuccess ? EmployeeErrorCode.SUCCESS : EmployeeErrorCode.FAIL), HttpStatus.OK);
    }

    @PutMapping("/{old_manager_id}/change/{new_manager_id}")
    public ResponseEntity<BaseResponse> changeManager(
            @PathVariable(value = "old_manager_id") int oldManagerId,
            @PathVariable(value = "new_manager_id") int newManagerId) {
        EmployeeDto employeeDto = employeeBusiness.changeManager(oldManagerId, newManagerId);
        return new ResponseEntity<>(new EmployeeResponse(EmployeeErrorCode.SUCCESS, employeeDto), HttpStatus.OK);
    }

    /**
     * @param httpServletRequest : HttpServletRequest
     * @return EmployeeListResponse
     */
    @PostMapping(value = "/import")
    public ResponseEntity<BaseResponse> importEmployees(HttpServletRequest httpServletRequest) throws IOException {
        List<String> employeeCodeErrors = employeeBusiness.importEmployee(httpServletRequest);

        if (employeeCodeErrors.size() == 0) {
            return new ResponseEntity<>(new BaseResponse(EmployeeErrorCode.SUCCESS), HttpStatus.OK);
        } else {
            StringBuilder result = new StringBuilder("Thêm danh sách nhân viên lỗi với các mã nhân viên: ");
            for (String employeeCodeError : employeeCodeErrors) {
                result.append(employeeCodeError).append(", ");
            }
            return new ResponseEntity<>(new BaseResponse(EmployeeErrorCode.FAIL.getCode(), result.substring(0, result.toString().length() - 2)), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/send-qrcode")
    public ResponseEntity<BaseResponse> sendQrCode(@RequestBody SendQrCodeRequest request) {
        boolean sendQrCodeSuccess = employeeBusiness.sendQrCode(request);
        return new ResponseEntity<>(new BaseResponse(sendQrCodeSuccess ? EmployeeErrorCode.SUCCESS : EmployeeErrorCode.FAIL), HttpStatus.OK);
    }
}