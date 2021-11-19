package com.example.spotround.modle;

public class Allotedseats
{
    String rank, applicationId, name, cetPercentage, caste, seat,seat_type,preference;

    public Allotedseats(String rank, String applicationId, String name, String cetPercentage, String caste, String seat, String seat_type, String preference) {
        this.rank = rank;
        this.applicationId = applicationId;
        this.name = name;
        this.cetPercentage = cetPercentage;
        this.caste = caste;
        this.seat = seat;
        this.seat_type = seat_type;
        this.preference = preference;
    }
    public Allotedseats()
    {

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

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
