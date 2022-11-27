package com.comit.services.camera.util;

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

    public boolean validStringPolygons(String polygons) {
        if (validField(polygons, "\\[(\\[(\\[\\d+,\\d+\\][,]*){3,}\\][,]*)*\\]")) {
            return !polygons.contains("][");
        }
        return false;
    }
}
