package com.comit.services.organization.server.repository;

import com.comit.services.organization.server.model.Organization;
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
