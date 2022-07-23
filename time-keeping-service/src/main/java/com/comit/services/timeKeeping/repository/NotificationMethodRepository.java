package com.comit.services.timeKeeping.repository;

import com.comit.services.timeKeeping.model.entity.NotificationMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationMethodRepository extends JpaRepository<NotificationMethod, Integer> {
    NotificationMethod findById(int id);
}
