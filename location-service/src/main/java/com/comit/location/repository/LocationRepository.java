package com.comit.location.repository;

import com.comit.location.model.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Location findByIdAndOrganizationId(int id, Integer organizationId);

    Location findById(int id);

    boolean existsByCodeAndOrganizationId(String code, Integer organizationId);

    Page<Location> findByOrganizationIdAndTypeOrderByUpdateAtDesc(Integer organizationId, String type, Pageable paging);

    Page<Location> findByOrganizationIdAndTypeAndNameContainingOrOrganizationIdAndTypeAndCodeContainingOrderByUpdateAtDesc(Integer organizationId, String type, String search, Integer organizationId1, String type1, String search1, Pageable paging);

    List<Location> findAllByOrganizationId(int organizationId);
}
