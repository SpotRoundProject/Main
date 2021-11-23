package com.example.spotround.modle;

public class CandidateListObj {
    String rank, applicationId, name, cetPercentage, caste;

    public CandidateListObj() {}

    public CandidateListObj(String rank, String applicationId, String name, String cetPercentage, String caste, String seat) {
        this.rank = rank;
        this.applicationId = applicationId;
        this.name = name;
        this.cetPercentage = cetPercentage;
        this.caste = caste;

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

    public String getCetPercentage() {
        return cetPercentage;
    }

    public void setCetPercentage(String cetPercentage) {
        this.cetPercentage = cetPercentage;
    }

    public String getCaste() {
        return caste;
    }

    public void setCaste(String caste) {
        this.caste = caste;
    }


    @Override
    public String toString() {
        return "CandidateListObj{" +
                "rank='" + rank + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", name='" + name + '\'' +
                ", cetPercentage='" + cetPercentage + '\'' +
                ", caste='" + caste + '\'' +
                '}';
    }
}
