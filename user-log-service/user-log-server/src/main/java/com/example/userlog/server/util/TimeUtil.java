package com.example.userlog.server.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimeUtil {
    // ================================ Time ================================

    public static String dateToStringMillis(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        return formatter.format(time);
    }

    public Date parsingDateStringToDate(String timestamp) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        return format.parse(timestamp);
    }
}
