package com.comit.services.organization.middleware;

import com.comit.services.organization.constant.Const;
import com.comit.services.organization.constant.OrganizationErrorCode;
import com.comit.services.organization.controller.request.OrganizationRequest;
import com.comit.services.organization.exception.RestApiException;
import com.comit.services.organization.util.ValidateField;
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
        String description = request.getDescription();

        if (name == null || name.trim().isEmpty()) {
            throw new RestApiException(OrganizationErrorCode.MISSING_NAME_FIELD);
        } else {
            request.setName(name.trim());
        }
        if (email != null) {
            if (!email.trim().isEmpty() && !validateField.validEmail(email.trim())) {
                throw new RestApiException(OrganizationErrorCode.EMAIL_INVALID);
            }
            if (email.equals("")) {
                request.setEmail(null);
            }
            else {
                request.setEmail(email.trim());
            }
        }
        if (phone != null) {
            if (!phone.trim().isEmpty() && !validateField.validPhone(phone.trim())) {
                throw new RestApiException(OrganizationErrorCode.EMAIL_INVALID);
            }
            if (phone.equals("")) {
                request.setPhone(null);
            }
            else {
                request.setPhone(phone.trim());
            }
        }

        if (description != null) {
            if (description.trim().length() > 500) {
                throw new RestApiException(OrganizationErrorCode.DESCRIPTION_EXCEEDS_CHARACTER);
            }
            request.setDescription(description.trim());
        }
    }

    @Override
    public void verifyUploadOrganization(MultipartFile file) {
        validateField.validFile(file, Const.EXCEL_TYPE);
    }
}
