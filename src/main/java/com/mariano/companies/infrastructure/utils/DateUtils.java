package com.mariano.companies.infrastructure.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    public static LocalDateTime getFirstDayOfLastMonth() {
        return LocalDate.now()
                .minusMonths(1)
                .withDayOfMonth(1)
                .atStartOfDay();
    }

    public static LocalDateTime getLastDayOfLastMonth() {
        return LocalDate.now()
                .minusMonths(1)
                .with(TemporalAdjusters.lastDayOfMonth())
                .atTime(LocalTime.MAX);
    }

}
