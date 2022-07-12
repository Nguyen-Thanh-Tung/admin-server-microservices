package com.comit.services.account.constant;

import java.util.List;

public interface Const {
    String ROLE_SUPER_ADMIN = "Super Admin";
    String ROLE_TIME_KEEPING_ADMIN = "Admin Quản lý chấm công";
    String ROLE_AREA_RESTRICTION_CONTROL_ADMIN = "Admin Kiểm soát khu vực hạn chế";
    String ROLE_TIME_KEEPING_USER = "Cán bộ Quản lý chấm công";
    String ROLE_AREA_RESTRICTION_CONTROL_USER = "Cán bộ Kiểm soát khu vực hạn chế";

    String TIME_KEEPING_MODULE = "Quản lý chấm công";
    String AREA_RESTRICTION_CONTROL_MODULE = "Kiểm soát khu vực hạn chế";


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
            ROLE_AREA_RESTRICTION_CONTROL_USER
    );

    // Type of file
    String IMAGE_TYPE = "image";
    String EXCEL_TYPE = "excel";
}
