package com.comit.services.employee.middleware;

import com.comit.services.employee.constant.EmployeeErrorCode;
import com.comit.services.employee.constant.GuestErrorCode;
import com.comit.services.employee.controller.request.GuestRequest;
import com.comit.services.employee.exception.RestApiException;
import com.comit.services.employee.util.ValidateField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestVerifyRequestServicesImpl implements GuestVerifyRequestServices {
    @Autowired
    ValidateField validateField;

    public void verifyAddGuestRequest(GuestRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String phone = request.getPhone();
        String image = request.getImage();

        if (name == null || name.trim().isEmpty()) {
            throw new RestApiException(GuestErrorCode.MISSING_NAME_FIELD);
        }

        if (email != null && !validateField.validEmail(email)) {
            throw new RestApiException(GuestErrorCode.EMAIL_IS_INVALID);
        }

        if (phone != null && !validateField.validPhone(phone)) {
            throw new RestApiException(GuestErrorCode.PHONE_IS_INVALID);
        }

        if (image == null) {
            throw new RestApiException(GuestErrorCode.MISSING_IMAGE_FIELD);
        }
    }
}
