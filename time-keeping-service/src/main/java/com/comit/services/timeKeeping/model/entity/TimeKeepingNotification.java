package com.comit.services.timeKeeping.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "time_keeping_notification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeKeepingNotification extends BaseModel {
    @Column(name = "late_time")
    private int lateTime;

    @Column(name = "late_in_week")
    private int lateInWeek;

    @Column(name = "late_in_month")
    private int lateInMonth;

    @Column(name = "late_in_quarter")
    private int lateInQuarter;

    // 0: Sunday, 1: Monday,...
    @Column(name = "start_day_of_week", columnDefinition = "int default 1")
    private int startDayOfWeek;

    @Column(name = "end_day_of_week", columnDefinition = "int default 5")
    private int endDayOfWeek;

    @Column(name = "notification_method_id")
    private Integer notificationMethodId;

    @Column(name = "location_id")
    private Integer locationId;
}
