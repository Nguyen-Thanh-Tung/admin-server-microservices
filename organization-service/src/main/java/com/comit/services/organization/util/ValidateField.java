package com.comit.services.organization.util;

import com.comit.services.organization.constant.Const;
import com.comit.services.organization.constant.OrganizationErrorCode;
import com.comit.services.organization.exception.RestApiException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidateField {
    public boolean validField(String value, String patternString) {
        Pattern pattern = Pattern.compile("^" + patternString + "$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public boolean validPhone(String phone) {
        return validField(phone, "\\d{10,15}");
    }

    public boolean validEmail(String email) {
        return validField(email, "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*");
    }

    public boolean validFile(MultipartFile file, String type) {
        if (file == null) {
            throw new RestApiException(OrganizationErrorCode.MISSING_FILE_FIELD);
        }
        String[] tmp = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String extension = tmp[tmp.length - 1];
        if (Objects.equals(type, Const.IMAGE_TYPE)) {
            if (extension == null
                    || (!Objects.equals(extension.toLowerCase(), "jpg")
                    && !Objects.equals(extension.toLowerCase(), "png")
                    && !Objects.equals(extension.toLowerCase(), "jpeg"))) {
                throw new RestApiException(OrganizationErrorCode.UN_SUPPORT_FILE_UPLOAD);
            }
        } else if (Objects.equals(type, Const.EXCEL_TYPE)) {
            if (extension == null
                    || (!Objects.equals(extension.toLowerCase(), "xlsx"))) {
                throw new RestApiException(OrganizationErrorCode.UN_SUPPORT_FILE_UPLOAD);
            }
        }
        return true;
    }
}
