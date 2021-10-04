package com.crg.learn.controller.test.support;

import java.time.*;
import java.util.Map;

public class BookingDates {

    private static final Map<String, Instant> BOOKING_DATES = Map.of(
            "jun_21_2021", whenBooked(6, 21, 16, 0),
            "jul_03_2021", whenBooked(7, 3, 8, 30),
            "aug_20_2021", whenBooked(7, 20, 10, 15)
    );

    private static Instant whenBooked(int month, int dayOfMonth, int hour, int minute) {
        return LocalDateTime.of(2021, month, dayOfMonth, hour, minute).toInstant(ZoneOffset.UTC);
    }

    public static Instant jun_21_2021() {
        return BOOKING_DATES.get("jun_21_2021");
    }

    public static Instant jul_03_2021() {
        return BOOKING_DATES.get("jul_03_2021");
    }

    public static Instant aug_20_2021() {
        return BOOKING_DATES.get("aug_20_2021");
    }

}
