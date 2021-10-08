package com.example.spotround.modle;

public class User
{
    String name,password, phone, applicationId, rank, email, cetPercentage, caste;
    boolean fromWalchand , appVerified;


    public User(){}

    public User(String name, String password, String phone, String applicationId, String rank, String email, String cetPercentage, String caste, boolean fromWalchand, boolean appVerified) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.applicationId = applicationId;
        this.rank = rank;
        this.email = email;
        this.cetPercentage = cetPercentage;
        this.caste = caste;
        this.fromWalchand = fromWalchand;  //1 - From fromWalchand, 0 - not from fromWalchand
        this.appVerified = appVerified; // 1 - Verified, 0 - Not Verified
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isFromWalchand() {
        return fromWalchand;
    }

    public void setFromWalchand(boolean fromWalchand) {
        this.fromWalchand = fromWalchand;
    }

    public boolean isAppVerified() {
        return appVerified;
    }

    public void setAppVerified(boolean appVerified) {
        this.appVerified = appVerified;
    }
}
