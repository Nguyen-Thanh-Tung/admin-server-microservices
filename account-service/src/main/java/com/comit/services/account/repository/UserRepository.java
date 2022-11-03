package com.comit.services.account.repository;

import com.comit.services.account.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    List<User> findAllByStatusNotIn(List<String> statusReject);

    List<User> findAllByParentId(int parentId);

    int countByLocationIdAndStatus(Integer locationId, String status);

    int countByOrganizationIdAndStatus(Integer organizationId, String status);

    int countByOrganizationIdAndStatusNotIn(Integer organizationId, List<String> statusReject);

    @Query("select count(u.id) from User u join u.roles r where r.id in :roleIds and u.organizationId = :organizationId")
    int getNumberUserOfRoles(
            @Param("organizationId") Integer organizationId,
            @Param("roleIds") List<Integer> roleIds);

    @Query("select count(u.id) from User u join u.roles r where r.id in :roleIds")
    int getNumberUserOfRoles(
            @Param("roleIds") List<Integer> roleIds);

    @Query("select count(distinct u.organizationId) from User u join u.roles r where r.id in :roleIds")
    int getNumberOrganizationOfRoles(@Param("roleIds") List<Integer> roleIds);

    List<User> findAllByStatusOrderByStatusAscIdDesc(String status);
}
