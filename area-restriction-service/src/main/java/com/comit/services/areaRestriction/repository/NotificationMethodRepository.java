package com.comit.services.areaRestriction.repository;

import com.comit.services.areaRestriction.model.entity.NotificationMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationMethodRepository extends JpaRepository<NotificationMethod, Integer> {
    NotificationMethod findById(int id);

    NotificationMethod findByAreaRestrictionId(int areaRestrictionId);
}
