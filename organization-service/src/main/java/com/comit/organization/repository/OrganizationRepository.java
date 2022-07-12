package com.comit.organization.repository;

import com.comit.organization.model.entity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Integer> {
    boolean existsByName(String name);

    Organization findById(int id);

    Organization findByName(String name);

    void deleteById(int id);

    void deleteByName(String name);

}
