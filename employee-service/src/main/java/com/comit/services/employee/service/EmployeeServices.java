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

    Employee getEmployee(int employeeId);

    List<Employee> getEmployees(String employeeCode);

    Employee saveEmployee(Employee employee);

    Employee getEmployeeByCodeAndLocation(String code, Integer locationId);

    List<Employee> saveAllEmployee(List<Employee> oldManagerEmployees);

    String getSaveEmployeeImageResponse(MultipartFile file, Integer locationId);

    String getUpdateEmployeeImageResponse(int id, MultipartFile file);

    String getDeleteEmployeeImageResponse(Integer id, Integer locationId);

    List<Employee> addEmployeeList(List<Employee> employees);

    List<Employee> getEmployeeListById(List<Integer> employeeIds);

    LocationDtoClient getLocationOfCurrentUser();

    MetadataDtoClient saveMetadata(String image_path);
    MetadataDtoClient saveMetadata(String image_path, int embeddingId, int locationId);

    boolean isTimeKeepingModule();

    boolean isAreaRestrictionModule();

    boolean isBehaviorModule();

    List<AreaEmployeeTimeDtoClient> saveEmployeeAreaRestrictionList(String areaEmployees, Integer newEmployeeId);

    void deleteEmployeeAreaRestrictionList(Integer employeeId);

    List<Employee> getEmployeeOfManager(int managerId);

    void deleteManagerOnAllAreaRestriction(int employeeId);

    void deleteAreaRestrictionManagerNotificationList(Integer employeeId);

    OrganizationDtoClient getOrganizationOfCurrentUser();

    int getNumberEmployeeOfLocation(Integer locationId);

    ShiftDtoClient getShift(int shiftId);

    MetadataDtoClient getMetadata(Integer imageId);

    List<AreaEmployeeTimeDtoClient> getAreaEmployeeTimesOfEmployee(int employeeId);

    LocationDtoClient getLocationById(Integer locationId);

    Employee getEmployeeByEmbeddingId(int embeddingId);
}
