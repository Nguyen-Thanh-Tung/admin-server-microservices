package com.comit.services.employee.client;

import com.comit.services.employee.client.response.CountEmployeeResponse;
import com.comit.services.employee.client.response.EmployeeResponse;

public interface EmployeeClient {
    EmployeeResponse getEmployee(String token, Integer id);
    CountEmployeeResponse getNumberEmployeeOfLocation(String token, Integer locationId);
}
