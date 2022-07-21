package com.comit.services.camera.repository;

import com.comit.services.camera.model.entity.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Integer> {
    void deleteById(int id);

    Camera findById(int id);

    Page<Camera> findByLocationIdInOrderByStatusAscIdDesc(List<Integer> locationIds, Pageable pageable);

    Page<Camera> findByLocationIdInAndNameContainingOrderByStatusAscIdDesc(List<Integer> locationIds, String search, Pageable pageable);

    Page<Camera> findByAreaRestrictionIdAndNameContainingOrderByStatusAscIdDesc(Integer areaRestrictionId, String search, Pageable paging);

    Page<Camera> findByAreaRestrictionIdOrderByStatusAscIdDesc(Integer areaRestrictionId, Pageable paging);

    Page<Camera> findByLocationIdInAndIdInAndNameContainingOrderByStatusAscIdDesc(List<Integer> locationIds, List<Integer> cameraIds, String search, Pageable pageable);

    Page<Camera> findByLocationIdInAndIdInOrderByStatusAscIdDesc(List<Integer> locationIds, List<Integer> cameraIds, Pageable pageable);

    Page<Camera> findByLocationIdInAndStatusAndNameContainingOrderByIdDesc(List<Integer> locationIds, String status, String search, Pageable pageable);

    Page<Camera> findByLocationIdInAndIdInAndStatusAndNameContainingOrderByStatusAscIdDesc(List<Integer> locationIds, List<Integer> cameraIds, String status, String search, Pageable pageable);

    Page<Camera> findByLocationIdInAndStatusOrderByIdDesc(List<Integer> locationIds, String status, Pageable pageable);

    Page<Camera> findByLocationIdInAndIdInAndStatusOrderByStatusAscIdDesc(List<Integer> locationIds, List<Integer> cameraIds, String status, Pageable pageable);

    Page<Camera> findByAreaRestrictionIdAndStatusAndNameContainingOrderByStatusAscIdDesc(Integer areaRestrictionId, String status, String search, Pageable paging);

    Page<Camera> findByAreaRestrictionIdAndStatusOrderByStatusAscIdDesc(Integer areaRestrictionId, String status, Pageable paging);

    Page<Camera> findByLocationIdInAndStatusOrderByStatusAscIdDesc(List<Integer> locationId, String status, Pageable pageable);

    Page<Camera> findByLocationIdInAndStatusAndNameContainingOrderByStatusAscIdDesc(List<Integer> locationId, String status, String search, Pageable pageable);

    Camera findByIdAndStatus(int id, String status);

    Camera findByLocationIdAndName(Integer locationId, String name);
}
