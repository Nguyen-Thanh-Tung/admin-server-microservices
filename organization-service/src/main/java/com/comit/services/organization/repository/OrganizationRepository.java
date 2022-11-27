package com.comit.services.organization.repository;

import com.comit.services.organization.model.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    boolean existsByName(String name);

    Organization findById(int id);

    Organization findByName(String name);

    void deleteById(int id);

    void deleteByName(String name);

    Page<Organization> findAllByNameIsNotAndEmailContainingOrNameIsNotAndNameContainingOrNameIsNotAndPhoneContainingOrderByIdDesc(
            String name1, String search1, String name2, String search2, String name3, String search3, Pageable pageable);

    Page<Organization> findAllByNameNotInOrderByIdDesc(List<String> name, Pageable pageable);
}
