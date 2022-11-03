package com.comit.services.timeKeeping.constant;

import com.comit.services.timeKeeping.model.entity.Shift;

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

    // method
    String POST = "POST";
    String GET = "GET";
    String PUT = "PUT";
    String DELETE = "DELETE";

    // action
    String ADD_SHIFT_LOCATION = "Thêm ca làm việc cho chi nhánh";
    String UPDATE_SHIFT_LOCATION = "Cập nhật ca làm việc cho chi nhánh";
    String DELETE_SHIFT_LOCATION = "Xóa ca làm việc cho chi nhánh";
    String DELETE_NOTIFICATION_LOCATION = "Xóa cài đặt chấm công chi nhánh";
    String ADD_NOTIFICATION_LOCATION = "Thêm thông tin cài đặt chấm công cho chi nhánh";
    String UPDATE_NOTIFICATION_ID = "Cập nhật thông tin chấm công";

    // app route
    String SHIFT_LOCATION_ID_AR = "/shifts/location/[0-9]+";
    String SHIFT_ID_AR = "/shifts/[0-9]+";
    String NOTIFICATION_LOCATION_ID_AR = "/time-keeping-notifications/location/[0-9]+";
    String NOTIFICATION_ID_AR = "/time-keeping-notifications/[0-9]+";

    // scope
    String INTERNAL = "internal";
}
