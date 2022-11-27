package com.comit.services.account.repository;

import com.comit.services.account.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<User> findAllByParentIdAndStatusNotIn(int id, List<String> statusReject);

    User findByUsernameAndStatusNotIn(String username, List<String> statusReject);

    User findByEmailAndStatusNotIn(String email, List<String> statusReject);

    void deleteById(int id);

    void deleteByUsername(String username);

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

    /* status = null, search = null */
    Page<User> findAllByParentIdOrderByIdDesc(int parentId, Pageable pageable);
    @Query("select u from User u join u.roles r where u.organizationId = ?1 and u.parent.id <> ?2 and r.name in ?3 group by u.id order by u.id desc")
    Page<User> findAllByOrganizationIdAndParentIdNotAndRoleNameOrderByIdDesc(int organizationId, int id, List<String> roleAccept, Pageable pageable);
    @Query("select u from User u join u.roles r where u.organizationId = ?1 and r.name = ?2 order by u.id desc")
    Page<User> findAllByOrganizationIdAndRoleNameOrderByIdDesc(int organizationId, String roleName, Pageable pageable);

    /* status != null, search = null */
    Page<User> findAllByParentIdAndStatusOrderByIdDesc(int parentId, String status, Pageable pageable);
    @Query("select u from User u join u.roles r where u.organizationId = ?1 and u.parent.id <> ?2 and r.name in ?3 and u.status = ?4 group by u.id order by u.id desc")
    Page<User> findAllByOrganizationIdAndParentIdNotAndRoleNameAndStatusOrderByIdDesc(int organizationId, int id, List<String> roleAccept, String status, Pageable pageable);
    @Query("select u from User u join u.roles r where u.organizationId = ?1 and r.name = ?2 and u.status = ?3 order by u.id desc")
    Page<User> findAllByOrganizationIdAndRoleNameAndStatusOrderByIdDesc(int organizationId, String roleName, String status, Pageable pageable);

    /* search != null, status = null */
    @Query("select u from User u where u.parent.id = ?1 and (u.username like %?2% or u.fullName like %?2% or u.email like %?2%) order by u.id desc")
    Page<User> findAllByParentIdAndSearchOrderByIdDesc(int parentId, String search, Pageable pageable);
    @Query("select u from User u join u.roles r where u.organizationId = ?1 and u.parent.id <> ?2 and r.name in ?3 and (u.username like %?4% or u.fullName like %?4% or u.email like %?4%) group by u.id order by u.id desc")
    Page<User> findAllByOrganizationIdAndParentIdNotAndRoleNameAndSearchOrderByIdDesc(int organizationId, int id, List<String> roleAccept, String search, Pageable pageable);
    @Query("select u from User u join u.roles r where u.organizationId = ?1 and r.name = ?2 and (u.username like %?3% or u.fullName like %?3% or u.email like %?3%) order by u.id desc")
    Page<User> findAllByOrganizationIdAndRoleNameAndSearchOrderByIdDesc(int organizationId, String roleName, String search, Pageable pageable);

    /* search != null, status != null */
    @Query("select u from User u where u.parent.id = ?1 and u.status = ?2 and (u.username like %?3% or u.fullName like %?3% or u.email like %?3%) order by u.id desc")
    Page<User> findAllByParentIdAndStatusAndSearchOrderByIdDesc(int parentId, String status, String search, Pageable pageable);
    @Query("select u from User u join u.roles r where u.organizationId = ?1 and u.parent.id <> ?2 and r.name in ?3 and u.status = ?4 and (u.username like %?5% or u.fullName like %?5% or u.email like %?5%) group by u.id order by u.id desc")
    Page<User> findAllByOrganizationIdAndParentIdNotAndRoleNameAndStatusAndSearchOrderByIdDesc(int organizationId, int id, List<String> roleAccept, String status, String search, Pageable pageable);
    @Query("select u from User u join u.roles r where u.organizationId = ?1 and r.name = ?2 and u.status = ?3 and (u.username like %?4% or u.fullName like %?4% or u.email like %?4%) order by u.id desc")
    Page<User> findAllByOrganizationIdAndRoleNameAndStatusAndSearchOrderByIdDesc(int organizationId, String roleName, String status, String search, Pageable pageable);

    List<User> findAllByParentIdOrderByStatusAscIdDesc(Integer id);

    List<User> findAllByStatusAndParentIdOrderByStatusAscIdDesc(String status, Integer id);

    List<User> findAllByLocationIdAndOrganizationIdAndStatusAndIdNot(int locationId, int organizationId, String status, Integer id);
}
