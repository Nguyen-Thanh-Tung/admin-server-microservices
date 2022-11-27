package com.comit.services.areaRestriction.util;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidateField {
    public boolean validField(String value, String patternString) {
        Pattern pattern = Pattern.compile("^" + patternString + "$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    public boolean validTimeStamp(String time) {
        return validField(time, "([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])");
    }
}
