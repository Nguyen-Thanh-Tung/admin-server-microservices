package com.comit.services.areaRestriction.server.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TimeUtil {
    // ================================ Time ================================
    public static DateTimeZone asiaHoChiMinh = DateTimeZone.forID("Asia/Ho_Chi_Minh");

    public static Date getDateTimeFromTimeString(String time) {
        DateTime now = new DateTime(asiaHoChiMinh);

        List<String> timeArray = List.of(time.split(":"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now.toDate());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray.get(0)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray.get(1)));
        calendar.set(Calendar.SECOND, Integer.parseInt(timeArray.get(2)));
        return calendar.getTime();
    }
}
