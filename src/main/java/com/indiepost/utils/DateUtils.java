package com.indiepost.utils;

import com.indiepost.dto.stat.TimeDomainStat;

import java.math.BigInteger;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jake on 17. 4. 28.
 */
public class DateUtils {
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date newDate(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return localDateToDate(localDate);
    }

    public static Date newDate(int year, int month, int day, int hour) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, 0);
        return localDateTimeToDate(localDateTime);
    }

    public static Date newDate(int year, int month, int day, int hour, int minute) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);
        return localDateTimeToDate(localDateTime);
    }

    public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        return localDateTimeToDate(localDateTime);
    }

    public static List<TimeDomainStat> normalizeTimeDomainStats(List<TimeDomainStat> list, LocalDateTime since, LocalDateTime until) {
        Duration duration = Duration.between(since, until);
        long hours = duration.toHours();
        if (hours > 48 || hours == list.size()) {
            return list;
        }

        LocalDate startDate = since.plusHours(9).toLocalDate();
        LocalDate endDate = until.plusHours(9).toLocalDate();
        int expectedHours = 23;
        if (!startDate.isEqual(endDate)) {
            expectedHours = 47;
        }

        int year = startDate.getYear();
        Month month = startDate.getMonth();
        int day = startDate.getDayOfMonth();
        LocalDate localDate = LocalDate.of(year, month, day);

        List<TimeDomainStat> results = new ArrayList<>();
        for (long h = 0; h <= expectedHours; ++h) {
            LocalDateTime ldt = localDate.atStartOfDay().plusHours(h);
            TimeDomainStat timeDomainStat = new TimeDomainStat(ldt, BigInteger.ZERO);
            results.add(timeDomainStat);
        }

        for (TimeDomainStat stat : list) {
            LocalDateTime statDateTime = stat.getStatDatetime();
            LocalDate statDate = stat.getStatDatetime().toLocalDate();
            int h = statDateTime.getHour();
            if (!statDate.isEqual(localDate)) {
                h = statDateTime.getHour() + 24;
            }
            results.get(h).setStatCount(stat.getStatCount());
        }
        return results;
    }

    public static Period getPeriod(LocalDateTime since, LocalDateTime until) {
        return Period.between(since.toLocalDate(), until.toLocalDate());
    }
}
