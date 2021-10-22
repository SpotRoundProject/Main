package com.example.spotround.modle;

import java.io.Serializable;

public class Application implements Serializable {
    private String name, applicationId, rank, phoneNo;
    private boolean payment, selfDec, clgSeat;

    public Application() {}

    public Application(String name, String applicationId, String rank, String phoneNo,
                       boolean clgSeat, boolean payment, boolean selfDec) {
        this.name = name;
        this.applicationId = applicationId;
        this.rank = rank;
        this.phoneNo = phoneNo;
        this.clgSeat = clgSeat;
        this.payment = payment;
        this.selfDec = selfDec;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
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
