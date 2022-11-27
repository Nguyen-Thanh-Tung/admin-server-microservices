package com.comit.services.organization.constant;

public interface Const {
    String DELETED = "deleted";
    String ACTIVE = "active";

    // Type of location
    String TIME_KEEPING_TYPE = "time_keeping";

    // Type of file
    String EXCEL_TYPE = "excel";

    Object IMAGE_TYPE = "image";

    // method
    String POST = "POST";
    String GET = "GET";
    String PUT = "PUT";
    String DELETE = "DELETE";

    // Action
    String ADD_ORG = "Thêm tổ chức";
    String ADD_LIST_ORG = "Thêm danh sách tổ chức";
    String ADD_USER_ORG = "Thêm người dùng vào tổ chức";
    String UPDATE_ORG = "Cập nhật thông tin tổ chức";
    String DELETE_ORG = "Xóa tổ chức";

    // app route
    String ORG_AR = "/organizations";
    String ORG_ID_AR = "/organizations/[0-9]+";
    String LIST_ORG_AR = "/organizations/import";
    String ORG_USER_AR = "/organizations/[0-9]+/users";

    // scope
    String INTERNAL = "internal";

    String DEFAULT_SIZE_PAGE = Integer.MAX_VALUE + "";
    String DEFAULT_PAGE = "0";
    String SUPER_ADMIN = "Super Admin";
}
