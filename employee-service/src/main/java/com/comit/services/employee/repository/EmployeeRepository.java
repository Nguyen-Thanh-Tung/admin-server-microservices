package com.comit.services.employee.repository;

import com.comit.services.employee.model.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Page<Employee> findByLocationIdOrderByStatusAscIdDesc(Integer locationId, Pageable pageable);

    Employee findByIdAndLocationId(int id, Integer locationId);

    Employee findById(int id);

    List<Employee> findByCode(String code);

    Employee findByCodeAndLocationId(String code, Integer locationId);

    List<Employee> findAllByIdIn(List<Integer> employeeIds);

    @Query("select e from Employee e where e.locationId = ?1 and e.status = ?2 and (e.name like %?3% or e.code like %?3% or e.email like %?3% or e.phone like %?3%)")
    Page<Employee> findByLocationIdAndStatusAndSearchOrderByIdDesc(
            Integer locationId, String status, String search, Pageable pageable);

    @Query("select e from Employee e where e.locationId = ?1 and (e.name like %?2% or e.code like %?2% or e.email like %?2% or e.phone like %?2%)")

    Page<Employee> findByLocationIdAndSearchOrderByIdDesc(
            Integer location, String search, Pageable pageable);

    Page<Employee> findByLocationIdAndStatusOrderByIdDesc(Integer locationId, String status, Pageable pageable);

    List<Employee> getByManagerId(int managerId);

    int countEmployeeByLocationIdAndStatus(Integer locationId, String status);

    Employee findByEmbeddingId(int embeddingId);

    boolean existsByEmail(String email);
}
