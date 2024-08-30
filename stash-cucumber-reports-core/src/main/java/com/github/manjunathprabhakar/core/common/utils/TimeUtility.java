package com.github.manjunathprabhakar.core.common.utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class TimeUtility {
    private static final int MICROSECOND_FACTOR = 1000000;

    public static String convertNanosecondsToTimeString(final long nanoseconds) {
        Duration durationMilliseconds = Duration.ofMillis(nanoseconds / MICROSECOND_FACTOR);
        long day = durationMilliseconds.toDays();
        long hour = durationMilliseconds.toHours();
        long minutes = durationMilliseconds.toMinutes();
        long seconds = durationMilliseconds.minusMinutes(minutes).getSeconds();
        long milliseconds = durationMilliseconds.minusMinutes(minutes).minusSeconds(seconds).toMillis();

        if (day == 0 && hour == 0 && minutes == 0 && seconds == 0) {
            return String.format("%03dms", milliseconds);
        }
        if (day == 0 && hour == 0 && minutes == 0) {
            return String.format("%02ds %03dms", seconds, milliseconds);
        }
        if (day == 0 && hour == 0) {
            return String.format("%dm %02ds %03dms", minutes, seconds, milliseconds);
        }
        if (day == 0) {
            return String.format("%dh %dm %02ds %03dms", hour, minutes, seconds, milliseconds);
        }

        return String.format("%dD %dh %dm %02ds %03dms", day, hour, minutes, seconds, milliseconds);
    }

    public static String convertSeconds(long nanosec) {
        double elapsedTimeInSeconds = TimeUnit.MILLISECONDS.convert(nanosec, TimeUnit.NANOSECONDS) / 1000.0;
        double h = elapsedTimeInSeconds / 3600;
        double m = (elapsedTimeInSeconds % 3600) / 60;
        double s = elapsedTimeInSeconds % 60;
        String sh = (h > 0 ? String.valueOf(h) + " " + "h" : "");
        String sm = (m < 10 && m > 0 && h > 0 ? "0" : "") + (m > 0 ? (h > 0 && s == 0 ? String.valueOf(m) : String.valueOf(m) + " " + "min") : "");
        String ss = (s == 0 && (h > 0 || m > 0) ? "" : (s < 10 && (h > 0 || m > 0) ? "0" : "") + String.valueOf(s) + " " + "sec");
        return sh + (h > 0 ? " " : "") + sm + (m > 0 ? " " : "") + ss;
    }

    public static String getCurrTimeStamp() {
        Date toPrint = new Date();//"yyyy-MM-dd HH:mm:ss.SSS z"
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss z");
        format.setTimeZone(TimeZone.getDefault());
        return (format.format(toPrint));
    }

    public static String getCurrTimeStampUnderscore() {
        Date toPrint = new Date();//"yyyy-MM-dd HH:mm:ss.SSS z"
        SimpleDateFormat format = new SimpleDateFormat("dd_MMM_yyyy_HH_mm_ss_z");
        format.setTimeZone(TimeZone.getDefault());
        return (format.format(toPrint));
    }

}
