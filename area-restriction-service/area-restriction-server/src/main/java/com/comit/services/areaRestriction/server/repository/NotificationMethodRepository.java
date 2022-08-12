package com.comit.services.areaRestriction.server.repository;

import com.comit.services.areaRestriction.server.model.NotificationMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationMethodRepository extends JpaRepository<NotificationMethod, Integer> {
    NotificationMethod findById(int id);

    NotificationMethod findByAreaRestrictionId(int areaRestrictionId);
}
