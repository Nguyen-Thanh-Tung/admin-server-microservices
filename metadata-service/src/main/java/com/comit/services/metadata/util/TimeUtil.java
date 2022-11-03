package com.comit.services.metadata.util;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class TimeUtil {
    // ================================ Time ================================
    public static DateTimeZone asiaHoChiMinh = DateTimeZone.forID("Asia/Ho_Chi_Minh");

    public static String getCurrentDateStr() {
        LocalDate now = new LocalDate(asiaHoChiMinh);
        return now.toString("yyyyMMdd");
    }
}
