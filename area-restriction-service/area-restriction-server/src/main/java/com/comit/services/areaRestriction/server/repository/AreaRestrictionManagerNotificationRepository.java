package com.comit.services.areaRestriction.server.repository;

import com.comit.services.areaRestriction.server.model.AreaRestrictionManagerNotification;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaRestrictionManagerNotificationRepository extends JpaRepository<AreaRestrictionManagerNotification, Integer> {
    void deleteById(@NotNull Integer id);

    void deleteAllByManagerId(Integer id);

    void deleteAllByAreaRestrictionId(Integer areaRestrictionId);

    List<AreaRestrictionManagerNotification> findAllByAreaRestrictionId(Integer areaRestrictionId);
}
