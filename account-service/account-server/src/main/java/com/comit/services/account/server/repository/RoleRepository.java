package com.comit.services.account.server.repository;

import com.comit.services.account.server.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    boolean existsByName(String name);

    Role findById(int id);

    Role findByName(String name);
}
