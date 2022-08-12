package com.comit.services.employee.server.middleware;

import com.comit.services.employee.server.constant.Const;
import com.comit.services.employee.server.constant.EmployeeErrorCode;
import com.comit.services.employee.server.exception.RestApiException;
import com.comit.services.employee.server.util.ValidateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployeeVerifyRequestServicesImpl implements EmployeeVerifyRequestServices {
    @Autowired
    private ValidateField validateField;

    @Override
    public void verifyAddEmployeeRequest(MultipartFile file, String name, String code, String email, String phone, String managerId, String shiftIds, boolean checkShifts) {
        validateField.validFile(file, Const.IMAGE_TYPE);

        if (name == null || name.trim().isEmpty()) {
            throw new RestApiException(EmployeeErrorCode.MISSING_NAME_FIELD);
        }

        if (code == null || code.trim().isEmpty()) {
            throw new RestApiException(EmployeeErrorCode.MISSING_EMPLOYEE_CODE_FIELD);
        }

        if (email != null && !validateField.validEmail(email)) {
            throw new RestApiException(EmployeeErrorCode.EMAIL_IS_INVALID);
        }

        if (phone != null && !validateField.validPhone(phone)) {
            throw new RestApiException(EmployeeErrorCode.PHONE_IS_INVALID);
        }

        if (managerId != null && !managerId.trim().isEmpty()) {
            try {
                Integer.parseInt(managerId);
            } catch (Exception e) {
                throw new RestApiException(EmployeeErrorCode.MANAGER_ID_IS_NOT_NUMBER);
            }
        }

        if (checkShifts && (shiftIds == null || shiftIds.trim().isEmpty())) {
            throw new RestApiException(EmployeeErrorCode.MISSING_SHIFT_IDS_FIELD);
        }

        if (checkShifts && !validateField.validIntArray(shiftIds)) {
            throw new RestApiException(EmployeeErrorCode.SHIFT_IDS_IS_INVALID);
        }
    }

    @Override
    public void verifyUpdateEmployeeRequest(MultipartFile file, String name, String code, String email, String phone, String managerId, String shiftIds) {

        if (name == null || name.trim().isEmpty()) {
            throw new RestApiException(EmployeeErrorCode.MISSING_NAME_FIELD);
        }

        if (code == null || code.trim().isEmpty()) {
            throw new RestApiException(EmployeeErrorCode.MISSING_EMPLOYEE_CODE_FIELD);
        }

        if (email != null && !validateField.validEmail(email)) {
            throw new RestApiException(EmployeeErrorCode.EMAIL_IS_INVALID);
        }

        if (phone != null && !validateField.validPhone(phone)) {
            throw new RestApiException(EmployeeErrorCode.PHONE_IS_INVALID);
        }

        if (managerId != null && !managerId.trim().isEmpty()) {
            try {
                Integer.parseInt(managerId);
            } catch (Exception e) {
                throw new RestApiException(EmployeeErrorCode.MANAGER_ID_IS_NOT_NUMBER);
            }
        }
    }

    @Override
    public void verifyUploadEmployee(MultipartFile file) {
        validateField.validFile(file, Const.EXCEL_TYPE);
    }
}
