package com.comit.services.account.repository;

import com.comit.services.account.model.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    boolean existsByName(String name);

    Role findById(int id);

    Role findByName(String name);
}
