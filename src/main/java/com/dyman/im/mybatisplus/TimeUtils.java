package com.dyman.im.mybatisplus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.*;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class TimeUtils {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    public static String formatLocalDateTime(@NotNull LocalDateTime time) {
        return formatLocalDateTime(time, DATE_TIME_FORMATTER);
    }

    public static String formatLocalDateTime(@NotNull LocalDateTime time, @NotNull DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    public static String formatLocalDate(@NotNull LocalDate time) {
        return formatLocalDate(time, DATE_FORMATTER);
    }

    public static String formatLocalDate(@NotNull LocalDate time, @NotNull DateTimeFormatter formatter) {
        return time.format(formatter);
    }

    public static LocalDateTime parseLocalDateTimeFromString(@NotBlank String time) {
        return parseLocalDateTimeFromString(time, DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parseLocalDateTimeFromString(@NotBlank String time, @NotNull DateTimeFormatter formatter) {
        return LocalDateTime.parse(time, formatter);
    }

    public static LocalDateTime parseLocalDateTimeFromSecond(long seconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), DEFAULT_ZONE);
    }

    public static LocalDateTime parseLocalDateTimeFromMillis(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), DEFAULT_ZONE);
    }

    public static LocalDate parseLocalDateFromString(@NotBlank String time) {
        return parseLocalDateFromString(time, DATE_FORMATTER);
    }

    public static LocalDate parseLocalDateFromString(@NotBlank String time, @NotNull DateTimeFormatter formatter) {
        return LocalDate.parse(time, formatter);
    }

    public static long toMillis(@NotNull LocalDateTime time) {
        return time.atZone(DEFAULT_ZONE).toInstant().toEpochMilli();
    }

    public static long toSeconds(@NotNull LocalDateTime time) {
        return time.atZone(DEFAULT_ZONE).toEpochSecond();
    }

    public static Date toDate(@NotNull LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(DEFAULT_ZONE).toInstant());
    }

    public static LocalDateTime toDateTime(@NotNull Date date) {
        return date.toInstant().atZone(DEFAULT_ZONE).toLocalDateTime();
    }

    public static boolean isBetween(@NotNull ChronoLocalDate current, @NotNull ChronoLocalDate begin, @NotNull ChronoLocalDate end) {
        return current.toEpochDay() >= begin.toEpochDay() && current.toEpochDay() <= end.toEpochDay();
    }

    public static boolean isBetween(@NotNull LocalTime current, @NotNull LocalTime begin, @NotNull LocalTime end) {
        return (current.isAfter(begin) || current.equals(begin)) && (current.isBefore(end) || current.equals(end));
    }
}
