package com.comit.services.history.server.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TimeUtil {
    // ================================ Time ================================
    public static DateTimeZone asiaHoChiMinh = DateTimeZone.forID("Asia/Ho_Chi_Minh");

    public static Date stringDateToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.parse(date);
    }

    public static String getCurrentDateTimeStr() {
        DateTime now = new DateTime(asiaHoChiMinh);
        return now.toString("yyyy-MM-dd_HH-mm-ss");
    }

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

    public static Date getFirstDayOfCurrentMonth() {
        LocalDate now = new LocalDate(asiaHoChiMinh);
        return now.withDayOfMonth(1).toDate();
    }

    public static Date getLastDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        LocalDate now = new LocalDate(asiaHoChiMinh);
        return now.withDayOfMonth(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).toDate();
    }
}
