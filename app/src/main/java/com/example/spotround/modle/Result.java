package com.example.spotround.modle;

public class Result {
    String preferenceNo, seatType, choiceCode;

    public Result(){}

    public Result(String preferenceNo, String seatType, String choiceCode) {
        this.preferenceNo = preferenceNo;
        this.seatType = seatType;
        this.choiceCode = choiceCode;
    }

    public String getPreferenceNo() {
        return preferenceNo;
    }

    public void setPreferenceNo(String preferenceNo) {
        this.preferenceNo = preferenceNo;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getChoiceCode() {
        return choiceCode;
    }

    public void setChoiceCode(String choiceCode) {
        this.choiceCode = choiceCode;
    }

    @Override
    public String toString() {
        return "Result{" +
                "preferenceNo='" + preferenceNo + '\'' +
                ", seatType='" + seatType + '\'' +
                ", choiceCode='" + choiceCode + '\'' +
                '}';
    }

    public String checkType() {
        int size = seatType.length() - 1;
        return seatType.substring(0, size);
    }

}
