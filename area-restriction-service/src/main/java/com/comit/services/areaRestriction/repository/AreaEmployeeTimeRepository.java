package com.comit.services.areaRestriction.repository;

import com.comit.services.areaRestriction.model.entity.AreaEmployeeTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaEmployeeTimeRepository extends JpaRepository<AreaEmployeeTime, Integer> {
    AreaEmployeeTime findByEmployeeIdAndAreaRestrictionId(Integer employeeId, Integer areaRestrictionId);

    void deleteByEmployeeId(Integer employeeId);

    int countByAreaRestrictionId(int areaRestrictionId);

    List<AreaEmployeeTime> findByEmployeeId(Integer employeeId);
}
