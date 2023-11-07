package com.wiprojobsearch.joblisting.utility;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class PostUtility {
    public static long calculatePostAgeInDays(Date createDate) {
        LocalDate currentDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        LocalDate postDate = createDate.toInstant().atZone(ZoneId.of("Asia/Kolkata")).toLocalDate();
        return ChronoUnit.DAYS.between(postDate, currentDate);
    }
}
