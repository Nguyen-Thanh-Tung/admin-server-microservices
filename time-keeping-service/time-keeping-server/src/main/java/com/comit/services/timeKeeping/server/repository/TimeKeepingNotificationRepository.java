package com.comit.services.timeKeeping.server.repository;

import com.comit.services.timeKeeping.server.model.TimeKeepingNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeKeepingNotificationRepository extends JpaRepository<TimeKeepingNotification, Integer> {
    TimeKeepingNotification findByIdAndLocationId(int id, Integer locationId);

    TimeKeepingNotification findByLocationId(Integer locationId);

    void deleteByLocationId(Integer locationId);
}
