package com.example.spotround.modle;

public class Preference {
    private String preference1, preference2, preference3, preference4, preference5, preference6, seatType, seatCode;
    boolean clgSeat;

    public Preference() {}

    public Preference(String preference1, String preference2, String preference3, String preference4, String preference5, String preference6, boolean clgSeat, String seatType, String seatCode) {
        this.preference1 = preference1;
        this.preference2 = preference2;
        this.preference3 = preference3;
        this.preference4 = preference4;
        this.preference5 = preference5;
        this.preference6 = preference6;
        this.clgSeat = clgSeat;
        this.seatCode = seatCode;
        this.seatType = seatType;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public boolean isClgSeat() {
        return clgSeat;
    }

    public void setClgSeat(boolean clgSeat) {
        this.clgSeat = clgSeat;
    }

    public String getPreference1() {
        return preference1;
    }

    public void setPreference1(String preference1) {
        this.preference1 = preference1;
    }

    public String getPreference2() {
        return preference2;
    }

    public void setPreference2(String preference2) {
        this.preference2 = preference2;
    }

    public String getPreference3() {
        return preference3;
    }

    public void setPreference3(String preference3) {
        this.preference3 = preference3;
    }

    public String getPreference4() {
        return preference4;
    }

    public void setPreference4(String preference4) {
        this.preference4 = preference4;
    }

    public String getPreference5() {
        return preference5;
    }

    public void setPreference5(String preference5) {
        this.preference5 = preference5;
    }

    public String getPreference6() {
        return preference6;
    }

    public void setPreference6(String preference6) {
        this.preference6 = preference6;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "preference1='" + preference1 + '\'' +
                ", preference2='" + preference2 + '\'' +
                ", preference3='" + preference3 + '\'' +
                ", preference4='" + preference4 + '\'' +
                ", preference5='" + preference5 + '\'' +
                ", preference6='" + preference6 + '\'' +
                ", seatType='" + seatType + '\'' +
                ", seatCode='" + seatCode + '\'' +
                ", clgSeat=" + clgSeat +
                '}';
    }

    public void check() {
        if(getPreference2().equals("Select Branch")) {
            setPreference2(getPreference1());
            setPreference3(getPreference1());
            setPreference4(getPreference1());
            setPreference5(getPreference1());
            setPreference6(getPreference1());
        }
        else if(getPreference3().equals("Select Branch")) {
            setPreference3(getPreference2());
            setPreference4(getPreference2());
            setPreference5(getPreference2());
            setPreference6(getPreference2());
        }
        else if(getPreference4().equals("Select Branch")) {
            setPreference4(getPreference3());
            setPreference5(getPreference3());
            setPreference6(getPreference3());
        }
        else if(getPreference5().equals("Select Branch")) {
            setPreference5(getPreference4());
            setPreference6(getPreference4());
        }
        else if(getPreference6().equals("Select Branch")) {
            setPreference6(getPreference5());
        }
    }
}
