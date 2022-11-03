package com.comit.services.metadata.constant;

public interface Const {

    // method
    String POST = "POST";
    String GET = "GET";
    String PUT = "PUT";
    String DELETE = "DELETE";

    // action
    String SAVE_FILE = "Thêm metadata với file";
    String PATH_FILE = "Thêm metadata với path";

    // app route
    String SAVE_METADATA_FILE_AR = "/metadatas/save-file";
    String SAVE_METADATA_PATH_AR = "/metadatas/save-path";

    // scope
    String INTERNAL = "internal";
}
