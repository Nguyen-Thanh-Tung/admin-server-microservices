package com.comit.services.camera.constant;

import java.util.List;

public interface Const {
    String ROLE_SUPER_ADMIN = "Super Admin";
    String ROLE_TIME_KEEPING_ADMIN = "Admin Quản lý chấm công";
    String ROLE_AREA_RESTRICTION_CONTROL_ADMIN = "Admin Kiểm soát khu vực hạn chế";
    String ROLE_TIME_KEEPING_USER = "Cán bộ Quản lý chấm công";
    String ROLE_AREA_RESTRICTION_CONTROL_USER = "Cán bộ Kiểm soát khu vực hạn chế";
    String ROLE_BEHAVIOR_CONTROL_USER = "Cán bộ Kiểm soát hành vi";

    String TIME_KEEPING_MODULE = "Quản lý chấm công";
    String AREA_RESTRICTION_CONTROL_MODULE = "Kiểm soát khu vực hạn chế";

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
            ROLE_AREA_RESTRICTION_CONTROL_USER
    );

    String MAIL_ORGANIZATION_NAME = "COMIT Corporation";
    String MAIL_LOCATION = "Hanoi, Vietnam";

    // Type of location
    String TIME_KEEPING_TYPE = "time_keeping";
    String AREA_RESTRICTION_TYPE = "area_restriction";

    String DAY_NOTIFICATION_TYPE = "Theo ngày";
    String MONTH_NOTIFICATION_TYPE = "Theo tháng";
    String WEEK_NOTIFICATION_TYPE = "Theo tuần";
    String QUARTER_NOTIFICATION_TYPE = "Theo quý";

    // Type of file
    String IMAGE_TYPE = "image";
    String EXCEL_TYPE = "excel";

    String DEFAULT_SIZE_PAGE = Integer.MAX_VALUE + "";
    String DEFAULT_PAGE = "0";
    String BEHAVIOR_TYPE = "behavior";

    String HEADER_MODULE = "module";
    String TIME_KEEPING_HEADER_MODULE = "time_keeping";
    String AREA_RESTRICTION_HEADER_MODULE = "area_restriction";
    String BEHAVIOR_HEADER_MODULE = "behavior";

    // method
    String POST = "POST";
    String GET = "GET";
    String PUT = "PUT";
    String DELETE = "DELETE";

    // action
    String ADD_CAMERA = "Thêm mới camera";
    String DELETE_CAMERA = "Xóa camera";
    String UPDATE_CAMERA = "Cập nhật thông tin camera";
    String GET_LIST_CAMERA = "Lấy danh sách camera";
    String GET_CAMERA = "Lấy thông tin camera";
    String UPDATE_CAMERA_POLYGON = "Cập nhật vùng hạn chế trên camera";
    String NUMBER_CAMERA_LOCATION = "Lấy số lượng camera của chi nhánh";
    String NUMBER_CAMERA_AREA = "Lấy số lượng camera của khu vực hạn chế";

    // app route
    String CAMERA_ID_AR = "/cameras/[0-9]+";
    String CAMERA_AR = "/cameras";
    String GET_CAMERA_AR = "/cameras/[0-9]+";
    String CAMERA_POLYGON_AR = "/cameras/[0-9]+/polygons";
    String NUMBER_CAMERA_LOCATION_AR = "/cameras/location/[0-9]+/number-camera";
    String NUMBER_CAMERA_AREA_AR = "/cameras/area-restriction/[0-9]+/number-camera";

    // scope api
    String INTERNAL = "internal";

    String CHECKIN = "Check in";
    String CHECKOUT = "Check out";
    String GSKVHC = "Giám sát KVHC";
    String KSHV = "Kiểm soát hành vi";
    String KSKVHC = "Kiểm soát khu vực hạn chế";
    String QLCC = "Quản lý chấm công";

    List<String> TK_CAMERA_TYPE = List.of(
            CHECKIN, CHECKOUT
    );
}
