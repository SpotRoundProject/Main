package com.example.spotround.datetime;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.TimeZone;


public class DateTime {
    static int hr, min, sec, year, month, date;
    static LocalDate dateTime;
    static {
        initialize();}
    public static void initialize() {
        SNTPClient.getDate(TimeZone.getTimeZone("Asia/Colombo"), new SNTPClient.Listener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onTimeReceived(String rawDate) {
                dateTime = LocalDate.of(Integer.parseInt(rawDate.substring(0, 4)),
                        Integer.parseInt(rawDate.substring(5, 7)),
                        Integer.parseInt(rawDate.substring(8, 10)));//yyyy-MM-dd'T'HH:mm:ssZ

                hr = Integer.parseInt(rawDate.substring(11,13));
                min = Integer.parseInt(rawDate.substring(14,16));
                sec = Integer.parseInt(rawDate.substring(17,19));
                year = Integer.parseInt(rawDate.substring(0,4));
                month = Integer.parseInt(rawDate.substring(5,7));
                date = Integer.parseInt(rawDate.substring(8,10));
            }

            @Override
            public void onError(Exception ex) {
                Log.e(SNTPClient.TAG, ex.getMessage());
            }
        });
    }

    public static int getHr() {
        return hr;
    }
    public static int getMin() {
        return min;
    }
    public static int getSec() {
        return sec;
    }
    public static int getYear() {
        return  year;
    }
    public static int getMonth() {
        return month;
    }
    public static int getDate() {
        return date;
    }
    public static LocalDate getLocalDate () {
        return  dateTime;
    }

}
