package com.comit.services.timeKeeping.repository;

import com.comit.services.timeKeeping.model.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Integer> {
    Shift findByIdAndLocationId(int id, Integer locationId);

    List<Shift> findShiftsByLocationId(Integer locationId);

    Shift findById(int id);
}
