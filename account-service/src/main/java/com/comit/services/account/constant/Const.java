package com.comit.services.account.constant;

import java.util.List;
import java.util.Map;

public interface Const {
    String ROLE_SUPER_ADMIN = "Super Admin";
    String ROLE_SUPER_ADMIN_ORGANIZATION = "Super Admin Organization";
    String ROLE_TIME_KEEPING_ADMIN = "Admin Quản lý chấm công";
    String ROLE_AREA_RESTRICTION_CONTROL_ADMIN = "Admin Kiểm soát khu vực hạn chế";
    String ROLE_TIME_KEEPING_USER = "Cán bộ Quản lý chấm công";
    String ROLE_AREA_RESTRICTION_CONTROL_USER = "Cán bộ Kiểm soát khu vực hạn chế";
    String ROLE_BEHAVIOR_CONTROL_ADMIN = "Admin Kiểm soát hành vi";
    String ROLE_BEHAVIOR_CONTROL_USER = "Cán bộ Kiểm soát hành vi";
    String TIME_KEEPING_MODULE = "Quản lý chấm công";
    String AREA_RESTRICTION_CONTROL_MODULE = "Kiểm soát khu vực hạn chế";
    String BEHAVIOR_CONTROL_MODULE = "Kiểm soát hành vi";

    // Status of user
    String PENDING = "pending";
    String ACTIVE = "active";
    String LOCK = "lock";
    String DELETED = "deleted";


    List<String> ROLES = List.of(
            ROLE_SUPER_ADMIN,
            ROLE_TIME_KEEPING_ADMIN,
            ROLE_TIME_KEEPING_USER,
            ROLE_AREA_RESTRICTION_CONTROL_ADMIN,
            ROLE_AREA_RESTRICTION_CONTROL_USER,
            ROLE_BEHAVIOR_CONTROL_ADMIN,
            ROLE_BEHAVIOR_CONTROL_USER
    );

    // Type of file
    String IMAGE_TYPE = "image";
    String EXCEL_TYPE = "excel";

    // Length username
    int MIN_LENGTH_USERNAME = 5;

    // method
    String POST = "POST";
    String GET = "GET";
    String PUT = "PUT";
    String DELETE = "DELETE";

    // action
    String ADD_USER = "Thêm người dùng";
    String UPDATE_USER = "Cập nhật thông tin người dùng";
    String DELETE_USER = "Xóa người dùng";
    String GET_USER = "Lấy thông tin người dùng";
    String GET_ALL_USER = "Lấy thông tin toàn bộ người dùng";
    String GET_ACCOUNT_NUMBER = "Lấy số lượng người dùng";
    String UPDATE_ROLES = "Cập nhật danh sách quyền cho người dùng";
    String LOCK_ACCOUNT = "Khóa hoặc mở khóa tài khoản";
    String UPDATE_AVATAR = "Thay đổi avatar người dùng";
    String NUMBER_USER_ORG = "Lấy số lượng người dùng của tổ chức";
    String USER_CURRENT = "Lấy thông tin người dùng hiện tại";
    String NUMBER_USER_CURRENT = "Lấy danh sách người dùng được tạo bởi người dùng hiện tại";
    String ROLES_USER_CURRENT = "Lấy danh sách quyền của người dùng hiện tại";
    String NUMBER_USER_OF_LOCATION = "Lấy số lượng người dùng của chi nhánh";
    String NUMBER_USER_OF_LIST_ROLE = "Lấy số lượng người dùng có một trong các quyền trong danh sách";
    String RESEND_CODE = "Gửi lại code tạo tài khoản vào mail người dùng";

    // app route
    String USER_AR = "/users";
    String USER_ID_AR = "/users/[0-9]+";
    String ACCOUNT_NUMBER_AR = "/users/account-number";
    String UPDATE_ROLES_AR = "/users/[0-9]+/roles";
    String LOCK_ACCOUNT_AR = "/users/[0-9]+/lock";
    String UPDATE_AVATAR_AR = "/users/[0-9]+/avatar";
    String NUMBER_USER_ORG_AR = "/users/organization/[0-9]+/number-user";
    String USER_CURRENT_AR = "/users/current";
    String NUMBER_USER_CURRENT_AR = "/users/current/users";
    String ROLES_USER_CURRENT_AR = "/users/current/roles";
    String NUMBER_USER_OF_LOCATION_AR = "/users/location/[0-9]+/number-user";
    String NUMBER_USER_OF_LIST_ROLE_AR = "/users/number-user-of-roles";
    String RESEND_CODE_AR = "/users/[0-9]+/resend-code";

    // scope api
    String INTERNAL = "internal";
    String ADMIN = "Admin";
    String CADRES = "Cán bộ";

    // location type
    String TIME_KEEPING = "time_keeping";
    String BEHAVIOR = "behavior";
    String AREA_RESTRICTION = "area_restriction";

    // key role
    String KEY_TIME_KEEPING = "chấm công";
    String KEY_BEHAVIOR = "hành vi";
    String KEY_AREA_RESTRICTION = "hạn chế";

    Map<String, String> MAP_ROLE_LOCATION_TYPE = Map.of(KEY_TIME_KEEPING, TIME_KEEPING, KEY_BEHAVIOR, BEHAVIOR, KEY_AREA_RESTRICTION, AREA_RESTRICTION);

    String DEFAULT_SIZE_PAGE = Integer.MAX_VALUE + "";
    String DEFAULT_PAGE = "0";
}
