package com.comit.services.account.repository;

import com.comit.services.account.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);

    boolean existsByEmailAndStatusNotIn(String email, List<String> statusReject);

    User findByIdAndStatusNotIn(int id, List<String> statusReject);

    User findByUsernameAndStatusNotIn(String username, List<String> statusReject);

    User findByEmailAndStatusNotIn(String email, List<String> statusReject);

    void deleteById(int id);

    void deleteByUsername(String username);

    List<User> findAllByStatusNotIn(List<String> deleted);

    List<User> findAllByParentId(int parentId);

    int countByLocationIdAndStatus(Integer locationId, String active);

    int countByOrganizationIdAndStatus(Integer organizationId, String active);
}
