package com.comit.services.timeKeeping.repository;

import com.comit.services.timeKeeping.model.entity.TimeKeepingNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeKeepingNotificationRepository extends JpaRepository<TimeKeepingNotification, Integer> {
    TimeKeepingNotification findByIdAndLocationId(int id, Integer locationId);

    TimeKeepingNotification findByLocationId(Integer locationId);

    void deleteByLocationId(Integer locationId);
}
