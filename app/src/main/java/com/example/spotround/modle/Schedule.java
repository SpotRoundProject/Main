package com.example.spotround.modle;

import java.io.Serializable;

public class Schedule implements Serializable {
    private String date,applicationFillingStart, applicationFillingEnd, round1Start, round1End, R1Result, round2Start, round2End, R2Result, round3Start, round3End, R3Result;

    public Schedule(){}

    public Schedule(String date,String applicationFillingStart, String applicationFillingEnd, String round1Start, String round1End, String r1Result, String round2Start, String round2End, String r2Result, String round3Start, String round3End, String r3Result) {
        if(date.length() == 9)
            date = date.substring(0, 4) + "0" +date.substring(4);
        this.date = date;
        this.applicationFillingStart = applicationFillingStart;
        this.applicationFillingEnd = applicationFillingEnd;
        this.round1Start = round1Start;
        this.round1End = round1End;
        R1Result = r1Result;
        this.round2Start = round2Start;
        this.round2End = round2End;
        R2Result = r2Result;
        this.round3Start = round3Start;
        this.round3End = round3End;
        R3Result = r3Result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicationFillingStart() {
        return applicationFillingStart;
    }

    public void setApplicationFillingStart(String applicationFillingStart) {
        this.applicationFillingStart = applicationFillingStart;
    }

    public String getApplicationFillingEnd() {
        return applicationFillingEnd;
    }

    public void setApplicationFillingEnd(String applicationFillingEnd) {
        this.applicationFillingEnd = applicationFillingEnd;
    }

    public String getRound1Start() {
        return round1Start;
    }

    public void setRound1Start(String round1Start) {
        this.round1Start = round1Start;
    }

    public String getRound1End() {
        return round1End;
    }

    public void setRound1End(String round1End) {
        this.round1End = round1End;
    }

    public String getR1Result() {
        return R1Result;
    }

    public void setR1Result(String r1Result) {
        R1Result = r1Result;
    }

    public String getRound2Start() {
        return round2Start;
    }

    public void setRound2Start(String round2Start) {
        this.round2Start = round2Start;
    }

    public String getRound2End() {
        return round2End;
    }

    public void setRound2End(String round2End) {
        this.round2End = round2End;
    }

    public String getR2Result() {
        return R2Result;
    }

    public void setR2Result(String r2Result) {
        R2Result = r2Result;
    }

    public String getRound3Start() {
        return round3Start;
    }

    public void setRound3Start(String round3Start) {
        this.round3Start = round3Start;
    }

    public String getRound3End() {
        return round3End;
    }

    public void setRound3End(String round3End) {
        this.round3End = round3End;
    }

    public String getR3Result() {
        return R3Result;
    }

    public void setR3Result(String r3Result) {
        R3Result = r3Result;
    }

    public int getYear() {
        return Integer.parseInt(date.substring(7));
                /*NOV , 17   120 1
                012 3 45 6 789 10*/

    }

    public int getDateInt() {
        return Integer.parseInt(date.substring(4, 6));
    }

    public int getMonth() {
        return getMonthInt(date.substring(0, 3));
    }


    private int getMonthInt(String month)
    {
        if(month.equals("JAN"))
            return 1;
        if(month.equals("FEB"))
            return 2;
        if(month.equals("MAR"))
            return 3;
        if(month.equals("APR"))
            return 4;
        if(month.equals("MAY"))
            return 5;
        if(month.equals("JUN"))
            return 6;
        if(month.equals("JUL"))
            return 7;
        if(month.equals("AUG"))
            return 8;
        if(month.equals("SEP"))
            return 9;
        if(month.equals("OCT"))
            return 10;
        if(month.equals("NOV"))
            return 11;
        if(month.equals("DEC"))
            return 12;
        return 1;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "date='" + date + '\'' +
                ", applicationFillingStart='" + applicationFillingStart + '\'' +
                ", applicationFillingEnd='" + applicationFillingEnd + '\'' +
                ", round1Start='" + round1Start + '\'' +
                ", round1End='" + round1End + '\'' +
                ", R1Result='" + R1Result + '\'' +
                ", round2Start='" + round2Start + '\'' +
                ", round2End='" + round2End + '\'' +
                ", R2Result='" + R2Result + '\'' +
                ", round3Start='" + round3Start + '\'' +
                ", round3End='" + round3End + '\'' +
                ", R3Result='" + R3Result + '\'' +
                '}';
    }
}
