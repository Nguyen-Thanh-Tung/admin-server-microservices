package com.comit.services.timeKeeping.server.constant;

import com.comit.services.timeKeeping.server.model.Shift;

import java.util.ArrayList;
import java.util.List;

public interface Const {
    List<Shift> SHIFTS = new ArrayList<>() {
        {
            add(new Shift("Ca sáng", "08:00:00", "12:00:00"));
            add(new Shift("Ca chiều", "14:00:00", "18:00:00"));
            add(new Shift("Ca đêm", "20:00:00", "24:00:00"));
            add(new Shift("Giờ hành chính", "08:00:00", "18:00:00"));
            add(new Shift("Tùy chọn", "08:00:00", "18:00:00"));
        }
    };
    int LATE_TIME_NOTIFICATION = 5; // Minutes

    int LATE_TIME_IN_WEEK = 3;
    int LATE_TIME_IN_MONTH = 3;
    int LATE_TIME_IN_QUARTER = 3;

    String DELETED = "deleted";
    String ACTIVE = "active";

    String WEEK_TYPE = "week";
    String MONTH_TYPE = "month";
    String QUARTER_TYPE = "quarter";

    String CHECK_IN = "Check in";
    String CHECK_OUT = "Check out";

}
