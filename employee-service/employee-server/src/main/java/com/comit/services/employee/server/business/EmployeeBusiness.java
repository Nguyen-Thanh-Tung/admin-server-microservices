package com.comit.services.employee.server.business;

import com.comit.services.employee.client.dto.EmployeeDto;
import com.comit.services.employee.client.request.SendQrCodeRequest;
import com.comit.services.employee.server.model.Employee;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface EmployeeBusiness {
    Page<Employee> getEmployeePage(String status, int page, int size, String search);

    List<EmployeeDto> getAllEmployee(List<Employee> content);

    EmployeeDto saveEmployee(HttpServletRequest servletRequest);

    EmployeeDto updateEmployee(int id, HttpServletRequest servletRequest);

    EmployeeDto getEmployee(int id, boolean isFullInfo);

    EmployeeDto getEmployee(String code);

    boolean deleteEmployee(int id);

    EmployeeDto changeManager(int oldManagerId, int newManagerId);

    List<String> importEmployee(HttpServletRequest httpServletRequest) throws IOException;

    boolean sendQrCode(SendQrCodeRequest request);

    int getNumberEmployeeOfLocation(Integer locationId);
}
