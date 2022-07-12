package com.comit.organization.util;
import com.comit.organization.constant.Const;
import com.comit.organization.constant.OrganizationErrorCode;
import com.comit.organization.exception.RestApiException;
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
        return validField(phone, "(03|05|07|08|09)+([0-9]{8})");
    }

    public boolean validEmail(String email) {
        return validField(email, "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}");
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
