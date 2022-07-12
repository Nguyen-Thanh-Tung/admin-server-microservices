package com.comit.organization.middleware;

import com.comit.organization.constant.Const;
import com.comit.organization.constant.OrganizationErrorCode;
import com.comit.organization.controller.request.OrganizationRequest;
import com.comit.organization.exception.RestApiException;
import com.comit.organization.util.ValidateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OrganizationVerifyRequestServicesImpl implements OrganizationVerifyRequestServices {
    @Autowired
    private ValidateField validateField;

    @Override
    public void verifyAddOrUpdateOrganization(OrganizationRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String phone = request.getPhone();

        if (name == null || name.trim().isEmpty()) {
            throw new RestApiException(OrganizationErrorCode.MISSING_NAME_FIELD);
        }

        if (email != null && !email.trim().isEmpty() && !validateField.validEmail(email)) {
            throw new RestApiException(OrganizationErrorCode.EMAIL_INVALID);
        }

        if (phone != null && !phone.trim().isEmpty() && !validateField.validPhone(phone)) {
            throw new RestApiException(OrganizationErrorCode.PHONE_INVALID);
        }
    }

    @Override
    public void verifyUploadOrganization(MultipartFile file) {
        validateField.validFile(file, Const.EXCEL_TYPE);
    }
}
