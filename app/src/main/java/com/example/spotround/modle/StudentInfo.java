package com.example.spotround.modle;

public class StudentInfo {
    String rank, applicationId, name, caste, gender, PCMPercentile, mathPercentile,
            physicsPercentile, chemPercentile, hsc, ssc;

    public StudentInfo() {}

    public StudentInfo(String rank, String applicationId, String name, String caste, String gender,
                       String PCMPercentile, String mathPercentile, String physicsPercentile,
                       String chemPercentile, String hsc, String ssc) {
        this.rank = rank;
        this.applicationId = applicationId;
        this.name = name;
        this.caste = caste;
        this.gender = gender;
        this.PCMPercentile = PCMPercentile;
        this.mathPercentile = mathPercentile;
        this.physicsPercentile = physicsPercentile;
        this.chemPercentile = chemPercentile;
        this.hsc = hsc;
        this.ssc = ssc;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPCMPercentile() {
        return PCMPercentile;
    }

    public void setPCMPercentile(String PCMPercentile) {
        this.PCMPercentile = PCMPercentile;
    }

    public String getMathPercentile() {
        return mathPercentile;
    }

    public void setMathPercentile(String mathPercentile) {
        this.mathPercentile = mathPercentile;
    }

    public String getPhysicsPercentile() {
        return physicsPercentile;
    }

    public void setPhysicsPercentile(String physicsPercentile) {
        this.physicsPercentile = physicsPercentile;
    }

    public String getChemPercentile() {
        return chemPercentile;
    }

    public void setChemPercentile(String chemPercentile) {
        this.chemPercentile = chemPercentile;
    }

    public String getHsc() {
        return hsc;
    }

    public void setHsc(String hsc) {
        this.hsc = hsc;
    }

    public String getSsc() {
        return ssc;
    }

    public void setSsc(String ssc) {
        this.ssc = ssc;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "rank='" + rank + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", name='" + name + '\'' +
                ", caste='" + caste + '\'' +
                ", gender='" + gender + '\'' +
                ", PCMPercentile='" + PCMPercentile + '\'' +
                ", mathPercentile='" + mathPercentile + '\'' +
                ", physicsPercentile='" + physicsPercentile + '\'' +
                ", chemPercentile='" + chemPercentile + '\'' +
                ", hsc='" + hsc + '\'' +
                ", ssc='" + ssc + '\'' +
                '}';
    }
}
