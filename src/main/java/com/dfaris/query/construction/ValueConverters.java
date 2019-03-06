package com.dfaris.query.construction;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class ValueConverters {

    public static String getSqlValueOf(LocalDateTime dateTime) {
        return getSqlValueOf(Timestamp.valueOf(dateTime).toString());
    }

    public static String getSqlValueOf(LocalTime time) {
        return getSqlValueOf(Time.valueOf(time).toString());
    }

    public static String getSqlValueOf(LocalDate date) {
        return getSqlValueOf(Date.valueOf(date).toString());
    }

    public static String getSqlValueOf(String s) {
        return '\'' + s + '\'';
    }

    public static String getSqlValueOf(Object e){

        if (e instanceof LocalDate) {
            return getSqlValueOf((LocalDate) e);
        } else if (e instanceof LocalDateTime) {
            return getSqlValueOf((LocalDateTime) e);
        } else if (e instanceof LocalTime) {
            return getSqlValueOf((LocalTime) e);
        } else if (e instanceof Number || e instanceof Boolean) {
            return e.toString();
        }

        return '\'' + e.toString() + '\'';
    }

}
