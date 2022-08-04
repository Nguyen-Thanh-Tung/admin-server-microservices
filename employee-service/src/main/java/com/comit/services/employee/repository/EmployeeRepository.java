package com.comit.services.employee.repository;

import com.comit.services.employee.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Page<Employee> findByLocationIdOrderByStatusAscIdDescIdDesc(Integer locationId, Pageable pageable);

    Employee findByIdAndLocationId(int id, Integer locationId);

    Employee findByCodeAndLocationId(String code, Integer locationId);

    Page<Employee> findByLocationIdAndNameContainingOrLocationIdAndCodeContainingOrderByStatusAscIdDescIdDesc(Integer location, String search, Integer location1, String search1, Pageable pageable);

    List<Employee> findAllByIdIn(List<Integer> employeeIds);

    Page<Employee> findByLocationIdAndStatusAndNameContainingOrLocationIdAndCodeContainingOrderByIdDescIdDesc(Integer locationId, String status, String search, Integer locationId1, String search1, Pageable pageable);

    Page<Employee> findByLocationIdAndStatusOrderByIdDescIdDesc(Integer locationId, String status, Pageable pageable);

    List<Employee> getByManagerId(int managerId);

    int countEmployeeByLocationIdAndStatus(Integer locationId, String status);
}
