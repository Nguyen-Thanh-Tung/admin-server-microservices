package com.comit.services.areaRestriction.repository;

import com.comit.services.areaRestriction.model.entity.AreaRestrictionManagerNotification;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRestrictionManagerNotificationRepository extends JpaRepository<AreaRestrictionManagerNotification, Integer> {
    void deleteById(@NotNull Integer id);

    void deleteAllByManagerId(Integer id);

    void deleteAllByAreaRestrictionId(Integer areaRestrictionId);
}
