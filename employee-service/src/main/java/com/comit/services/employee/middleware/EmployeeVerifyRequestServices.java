package com.comit.services.employee.middleware;

import org.springframework.web.multipart.MultipartFile;

public interface EmployeeVerifyRequestServices {
    void verifyAddEmployeeRequest(MultipartFile file, String name, String code, String email, String phone, String managerId, String shiftIds, boolean checkShifts);

    void verifyUpdateEmployeeRequest(MultipartFile file, String name, String code, String email, String phone, String managerId, String shiftIds);

    void verifyUploadEmployee(MultipartFile file);
}
