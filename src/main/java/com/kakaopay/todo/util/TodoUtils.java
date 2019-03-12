package com.kakaopay.todo.util;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class TodoUtils {
    private static DateTimeFormatter DATE_FORMAT =
        new DateTimeFormatterBuilder().appendPattern("yyyyMMdd[[HH][:mm][:ss][.SSS]]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

    static public DateTimeFormatter getDateTimeFormatter(){
        return DATE_FORMAT;
    }
}
