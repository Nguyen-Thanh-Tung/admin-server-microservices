package com.comit.services.areaRestriction.server.repository;

import com.comit.services.areaRestriction.server.model.AreaRestriction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRestrictionRepository extends JpaRepository<AreaRestriction, Integer> {
    Page<AreaRestriction> findByLocationIdOrderByUpdateAtDesc(Integer locationId, Pageable pageable);

    AreaRestriction findByLocationIdAndId(Integer locationId, Integer id);

    AreaRestriction findByLocationIdAndName(Integer location, String name);

    AreaRestriction findById(int id);

    Page<AreaRestriction> findByLocationIdAndNameContainingOrLocationIdAndCodeContainingOrderByUpdateAtDesc(Integer location, String search, Integer location1, String search1, Pageable pageable);

    List<AreaRestriction> findByManagerIdsOrManagerIdsContaining(String managerId, String managerIdWithComma);
}
