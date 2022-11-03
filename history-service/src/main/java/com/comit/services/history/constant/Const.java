package com.comit.services.history.constant;

import java.util.List;

public interface Const {
    String ROLE_SUPER_ADMIN = "Super Admin";
    String ROLE_TIME_KEEPING_ADMIN = "Admin Quản lý chấm công";
    String ROLE_AREA_RESTRICTION_CONTROL_ADMIN = "Admin Kiểm soát khu vực hạn chế";
    String ROLE_BEHAVIOR_CONTROL_ADMIN = "Admin Kiểm soát hành vi";
    String ROLE_TIME_KEEPING_USER = "Cán bộ Quản lý chấm công";
    String ROLE_AREA_RESTRICTION_CONTROL_USER = "Cán bộ Kiểm soát khu vực hạn chế";
    String ROLE_BEHAVIOR_CONTROL_USER = "Cán bộ Kiểm soát hành vi";

    String TIME_KEEPING_MODULE = "Quản lý chấm công";
    String AREA_RESTRICTION_CONTROL_MODULE = "Kiểm soát khu vực hạn chế";
    String BEHAVIOR_CONTROL_MODULE = "Kiểm soát hành vi";

    long TEN_MINUTE = 600000;
    long TWO_HOUR = 7200000;
    long ONE_DAY = 86400000;
    long TWO_WEEK = 1184400000;
    long ONE_MONTH = 2592000000L;
    long NINETY_DAY = 7776000000L;
    long ONE_YEAR = 31104000000L;
    int RATE_DEFAULT_VALUE = 100;
    int RATE_DEFAULT_INTERVAL = 1;
    String HEADER_REQUEST_ID = "x-request-id";
    String HEADER_REQUEST_TIME = "x-request-time";
    String HEADER_USERNAME = "x-username";

    // Status of user
    String PENDING = "pending";
    String ACTIVE = "active";
    String LOCK = "lock";
    String DELETED = "deleted";

    // Status of camera
    String ON = "on";
    String OFF = "off";


    List<String> ROLES = List.of(
            ROLE_SUPER_ADMIN,
            ROLE_TIME_KEEPING_ADMIN,
            ROLE_TIME_KEEPING_USER,
            ROLE_AREA_RESTRICTION_CONTROL_ADMIN,
            ROLE_AREA_RESTRICTION_CONTROL_USER,
            ROLE_BEHAVIOR_CONTROL_ADMIN,
            ROLE_BEHAVIOR_CONTROL_USER
    );

    String MAIL_ORGANIZATION_NAME = "COMIT Corporation";
    String MAIL_LOCATION = "Hanoi, Vietnam";

    // Type of location
    String TIME_KEEPING_TYPE = "time_keeping";
    String AREA_RESTRICTION_TYPE = "area_restriction";
    String BEHAVIOR_TYPE = "behavior";

    String DAY_NOTIFICATION_TYPE = "Theo ngày";
    String MONTH_NOTIFICATION_TYPE = "Theo tháng";
    String WEEK_NOTIFICATION_TYPE = "Theo tuần";
    String QUARTER_NOTIFICATION_TYPE = "Theo quý";

    // Type of file
    String IMAGE_TYPE = "image";
    String EXCEL_TYPE = "excel";

    String DEFAULT_SIZE_PAGE = Integer.MAX_VALUE + "";
    String DEFAULT_PAGE = "0";
    String CHECK_IN = "Check in";

    // method
    String POST = "POST";
    String GET = "GET";
    String PUT = "PUT";
    String DELETE = "DELETE";

    // action
    String IN_OUT_HISTORY = "Thêm lịch sử chấm công";
    String NOTIFICATION_HISTORY_ID = "Cập nhật trạng thái lịch sử cảnh báo";
    String NOTIFICATION_HISTORY = "Thêm mới lịch sử cảnh báo chấm công hoặc cảnh báo đột nhập khu vực hạn chế";

    // app route
    String IN_OUT_HISTORY_AR = "/in-out-histories";
    String NOTIFICATION_HISTORY_ID_AR = "notification-histories/[0-9]+/update-status";
    String NOTIFICATION_HISTORY_AR = "/histories/notification-histories";

    // scope
    String INTERNAL = "internal";

    // date format
    String DATE_FORMAT_COMMON = "dd/MM/yyyy HH:mm:ss";
}
