package com.comit.services.employee.service;

import com.comit.services.employee.client.data.*;
import com.comit.services.employee.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeServices {
    Page<Employee> getEmployeePage(Integer locationId, String status, String search, Pageable pageable);

    Employee getEmployee(int employeeId, Integer locationId);

    Employee getEmployee(String employeeCode, Integer locationId);

    Employee saveEmployee(Employee employee);

    Employee getEmployeeByCodeAndLocation(String code, Integer locationId);

    List<Employee> saveAllEmployee(List<Employee> oldManagerEmployees);

    boolean hasPermissionManageEmployee(String locationType);

    String getSaveEmployeeImageResponse(MultipartFile file, Integer locationId);

    String getUpdateEmployeeImageResponse(int id, MultipartFile file);

    String getDeleteEmployeeImageResponse(Integer id, Integer locationId);

    List<Employee> addEmployeeList(List<Employee> employees);

    List<Employee> getEmployeeListById(List<Integer> employeeIds);

    LocationDto getLocationOfCurrentUser();

    MetadataDto saveMetadata(String image_path);

    boolean isTimeKeepingModule();

    boolean isAreaRestrictionModule();

    boolean isBehaviorModule();

    List<AreaEmployeeTimeDto> saveEmployeeAreaRestrictionList(String areaEmployees, Integer newEmployeeId);

    void deleteEmployeeAreaRestrictionList(Integer employeeId);

    List<Employee> getEmployeeOfManager(int managerId);

    void deleteManagerOnAllAreaRestriction(int employeeId);

    void deleteAreaRestrictionManagerNotificationList(Integer employeeId);

    OrganizationDto getOrganizationOfCurrentUser();

    void sendQrCodeEmail(String mailTo, String fullname, String employeeCode, String organizationName, String locationName, String locationCode);

    int getNumberEmployeeOfLocation(Integer locationId);

    ShiftDto getShift(int shiftId);

    MetadataDto getMetadata(Integer imageId);

    List<AreaEmployeeTimeDto> getAreaEmployeeTimesOfEmployee(int employeeId);
}
