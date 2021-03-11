package com.hesabischool.hesabiapp.Clases;

import android.content.Context;
import android.content.res.Resources;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Time {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int MONTH_MILLS = 30 * DAY_MILLIS;

    public static String getTimeAgo(String date_time) {
        String string = date_time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       // System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
        long time=date.getTime();


        if (time < 1000000000000L) {
            //if timestamp given in seconds, convert to millis time *= 1000;
        }
        Date currentTime = Calendar.getInstance().getTime();
        long now =currentTime.getTime();
              //  getCurrentTime(ctx);
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize

        final long diff = now - time;

        if (diff < MINUTE_MILLIS) { return "همین الان"; }
        else if (diff < 2 * MINUTE_MILLIS) { return "چند دقیقه پیش"; }
        else if (diff < 50 * MINUTE_MILLIS) { return diff / MINUTE_MILLIS + " دقیقه پیش"; }
        else if (diff < 90 * MINUTE_MILLIS) { return "چند ساعت پیش"; }
        else if (diff < 24 * HOUR_MILLIS) { return diff / HOUR_MILLIS + " ساعت پیش"; }
        else if (diff < 48 * HOUR_MILLIS) { return "دیروز"; }
        else if(diff<30*DAY_MILLIS){return diff / DAY_MILLIS + " روز پیش";}
        else { return (diff /MONTH_MILLS) + " روز پیش"; }
    }

    public static String getTimeAgo2(String date_time) {

        if(date_time==null||date_time.equals("null"))
        {
            return "بدون زمان";
        }
        String string = date_time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
        long time=date.getTime();
        long diff = new Date().getTime() - time;
        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double years = days / 365;

        String words;

        if (seconds < 45) {
            words ="همین الان";
        } else if (seconds < 90) {
            words = "یک دقیقه پیش";
        } else if (minutes < 45) {
            words = Math.round(minutes)+"دقیقه پیش";
        } else if (minutes < 90) {
            words = "یک ساعت قبل";
        } else if (hours < 24) {
            words = Math.round(hours)+" ساعت قبل";
        } else if (hours < 42) {
            words = "یک روز قبل";
        } else if (days < 30) {
            words =  Math.round(days)+" روز قبل";
        } else if (days < 45) {
            words = "یک ماه قبل";
        } else if (days < 365) {
            words = Math.round(days / 30)+" ماه قبل";
        } else if (years < 1.5) {
            words = "یک سال قبل";
        } else {
            words = Math.round(years)+"سال قبل";
        }


        return words;
    }
}
