package com.comit.services.employee.service;

import com.comit.services.employee.model.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeServices {
    Page<Employee> getEmployeePage(Integer locationId, String status, String search, Pageable pageable);

    Employee getEmployee(int employeeId, Integer locationId);

    Employee saveEmployee(Employee employee);

    Employee getEmployeeByCodeAndLocation(String code, Integer locationId);

    List<Employee> saveAllEmployee(List<Employee> oldManagerEmployees);

    boolean hasPermissionManageEmployee(String locationType);

    String getSaveEmployeeImageResponse(MultipartFile file, Integer locationId);

    String getUpdateEmployeeImageResponse(int id, MultipartFile file);

    String getDeleteEmployeeImageResponse(Integer id, Integer locationId);

    List<Employee> addEmployeeList(List<Employee> employees);

    List<Employee> getEmployeeListById(List<Integer> employeeIds);

    Location getLocationOfCurrentUser();

    Metadata saveMetadata(String image_path);

    boolean isTimeKeepingModule();

    boolean isAreaRestrictionModule();

    boolean isBehaviorModule();

    List<AreaEmployeeTime> saveEmployeeAreaRestrictionList(String areaEmployees, Integer newEmployeeId);

    void deleteEmployeeAreaRestrictionList(Integer employeeId);

    List<Employee> getEmployeeOfManager(int managerId);

    List<AreaRestriction> getAreaRestrictions(int employeeId);

    void deleteAreaRestrictionManagerNotificationList(Integer employeeId);

    Organization getOrganizationOfCurrentUser();

    void sendQrCodeEmail(String mailTo, String fullname, String employeeCode, String organizationName, String locationName, String locationCode);

    int getNumberEmployeeOfLocation(Integer locationId);

    Shift getShift(int shiftId);

    Metadata getMetadata(Integer imageId);
}
