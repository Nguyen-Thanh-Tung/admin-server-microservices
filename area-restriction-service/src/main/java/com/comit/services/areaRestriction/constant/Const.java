package com.comit.services.areaRestriction.constant;

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

    // method
    String POST = "POST";
    String GET = "GET";
    String PUT = "PUT";
    String DELETE = "DELETE";

    // action
    String ADD_AREA = "Thêm khu vực hạn chế";
    String UPDATE_AREA = "Cập nhật thông tin khu vực hạn chế";
    String DELETE_AREA = "Xóa khu vực hạn chế";
    String UPDATE_AREA_NOTIFICATION = "Cập nhật thông tin cài đặt khu vực hạn chế";
    String DELETE_AREA_MANAGER = "Xóa quản lý khỏi tất cả các khu vực đang quản lý";
    String DELETE_AREA_MANAGER_NOTIFICATION = "Xóa quản lý khỏi tất cả các khu vực đang được thêm để nhận cảnh báo";
    String ADD_AREA_EMPLOYEE_TIME = "Thêm danh sách khu vực hạn chế và thời gian cho phép vào khu vực của nhân viên";

    // app route
    String AREA_AR = "/area-restrictions";
    String AREA_ID_AR = "/area-restrictions/[0-9]+";
    String AREA_ID_NOTIFICATION_AR = "/area-restrictions/[0-9]+/notification";
    String AREA_MANAGER_AR = "/area-restrictions/manager/[0-9]+";
    String AREA_MANAGER_NOTIFICATION_AR = "/area-restrictions/manager/[0-9]+/notification-manager";
    String AREA_EMPLOYEE_TIME_AR = "/area-restrictions/area-employee-times";

    // scope api
    String INTERNAL = "internal";
}
