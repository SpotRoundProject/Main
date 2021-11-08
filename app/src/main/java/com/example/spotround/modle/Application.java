package com.example.spotround.modle;

import java.io.Serializable;

public class Application implements Serializable {
    private String name, applicationId, phoneNo, seatType, seatCode;
    private boolean payment, selfDec, clgSeat;
    long rank;

    public Application() {}

    public Application(String name, String applicationId, long rank, String phoneNo,
                       boolean clgSeat, boolean payment, boolean selfDec) {
        this.name = name;
        this.applicationId = applicationId;
        this.rank = rank;
        this.phoneNo = phoneNo;
        this.clgSeat = clgSeat;
        this.payment = payment;
        this.selfDec = selfDec;
    }

    public Application(String name, String applicationId, long rank, String phoneNo, String seatType,
                       String seatCode, boolean payment, boolean selfDec, boolean clgSeat) {
        this.name = name;
        this.applicationId = applicationId;
        this.rank = rank;
        this.phoneNo = phoneNo;
        this.seatType = seatType;
        this.seatCode = seatCode;
        this.payment = payment;
        this.selfDec = selfDec;
        this.clgSeat = clgSeat;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public boolean isClgSeat() {
        return clgSeat;
    }

    public void setClgSeat(boolean clgSeat) {
        this.clgSeat = clgSeat;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public boolean isSelfDec() {
        return selfDec;
    }

    public void setSelfDec(boolean selfDec) {
        this.selfDec = selfDec;
    }

    @Override
    public String toString() {
        return "Application{" +
                "name='" + name + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", rank='" + rank + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", payment=" + payment +
                ", selfDec=" + selfDec +
                ", clgSeat=" + clgSeat +
                '}';
    }
}
