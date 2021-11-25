package com.example.spotround.datetime;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.TimeZone;


public class DateTime {
    static int hr, min, sec, year, month, date;
    static LocalDate dateTime;
    static Calendar cal = Calendar.getInstance();
    static String dateTimeString;
    static {
        initialize();}
    public static void initialize() {
        SNTPClient.getDate(TimeZone.getTimeZone("Asia/Colombo"), new SNTPClient.Listener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeReceived(String rawDate) {
                dateTimeString = rawDate;
                //yyyy-MM-dd'T'HH:mm:ssZ
                cal.set(getYear(), getMonth(), getDate(), getHr(), getMin(), getSec());
                scheduleUpdate();
            }

            @Override
            public void onError(Exception ex) {
                Log.e(SNTPClient.TAG, ex.getMessage());
            }
        });
    }

    public static int getHr() {
        //hr = Integer.parseInt(dateTimeString.substring(11,13));
        hr = cal.get(Calendar.HOUR_OF_DAY);
        return hr;
    }
    public static int getMin() {
        //min = Integer.parseInt(dateTimeString.substring(14,16));
        min = cal.get(Calendar.MINUTE);
        return min;
    }
    public static int getSec() {
        //sec = Integer.parseInt(dateTimeString.substring(17,19));
        sec = cal.get(Calendar.SECOND);
        return sec;
    }
    public static int getYear() {
        //year = Integer.parseInt(dateTimeString.substring(0,4));
        year = cal.get(Calendar.YEAR);
        return  year;
    }
    public static int getMonth() {
        //month = Integer.parseInt(dateTimeString.substring(5,7));
        month = cal.get(Calendar.MONTH);
        return month;
    }
    public static int getDate() {
        //date = Integer.parseInt(dateTimeString.substring(8,10));
        date = cal.get(Calendar.DATE);
        return date;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate getLocalDate () {
        dateTime = LocalDate.of(getYear(), getMonth()+1, getDate());
        return  dateTime;
    }

    static void scheduleUpdate() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                cal.add(Calendar.SECOND, 1);
            }
        }, 1000);
    }

}
