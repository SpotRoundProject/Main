package com.example.spotround;

public class ProfileInfo
{
    private String Name;
    private String DOB;
    private String Gender;
    private String Caste;
    private String EWS;
    private String PWD;
    private String HU;
    private String Orphan;
    private String Defense;
    private String HSC_Percentage;
    private String SSC_Percentage;
    private String PCM_Percentage;
    private String State_merit_rank;
    private String CET_percentile;
    private String First_preference;
    private String Second_preference;
    private String Third_preference;
    private String Forth_preference;
    private String Fifth_preference;
    private String Sixth_preference;
    private String status1;

    public ProfileInfo(String name, String DOB, String gender, String caste, String EWS, String PWD, String HU, String orphan, String defense, String HSC_Percentage, String SSC_Percentage, String PCM_Percentage, String state_merit_rank, String CET_percentile) {
        this.Name = name;
        this.DOB = DOB;
        this.Gender = gender;
        this.Caste = caste;
        this.EWS = EWS;
        this.PWD = PWD;
        this.HU = HU;
        this.Orphan = orphan;
        this.Defense = defense;
        this.HSC_Percentage = HSC_Percentage;
        this.SSC_Percentage = SSC_Percentage;
        this.PCM_Percentage = PCM_Percentage;
        this.State_merit_rank = state_merit_rank;
        this.CET_percentile = CET_percentile;
    }

    public ProfileInfo(String name, String DOB, String gender, String caste, String EWS, String PWD, String HU, String orphan, String defense, String HSC_Percentage, String SSC_Percentage, String PCM_Percentage, String state_merit_rank, String CET_percentile, String first_preference, String second_preference, String third_preference, String forth_preference, String fifth_preference, String sixth_preference, String status) {
        Name = name;
        this.DOB = DOB;
        Gender = gender;
        Caste = caste;
        this.EWS = EWS;
        this.PWD = PWD;
        this.HU = HU;
        Orphan = orphan;
        Defense = defense;
        this.HSC_Percentage = HSC_Percentage;
        this.SSC_Percentage = SSC_Percentage;
        this.PCM_Percentage = PCM_Percentage;
        State_merit_rank = state_merit_rank;
        this.CET_percentile = CET_percentile;
        First_preference = first_preference;
        Second_preference = second_preference;
        Third_preference = third_preference;
        Forth_preference = forth_preference;
        Fifth_preference = fifth_preference;
        Sixth_preference = sixth_preference;
        this.status1 = status;
    }

    public ProfileInfo(String name, String DOB, String gender, String caste, String EWS, String PWD, String HU, String orphan, String defense, String HSC_Percentage, String SSC_Percentage, String PCM_Percentage, String state_merit_rank, String CET_percentile, String first_preference, String second_preference, String third_preference, String forth_preference, String fifth_preference, String sixth_preference) {
        this.Name = name;
        this.DOB = DOB;
        this.Gender = gender;
        this.Caste = caste;
        this.EWS = EWS;
        this.PWD = PWD;
        this.HU = HU;
        this.Orphan = orphan;
        this.Defense = defense;
        this.HSC_Percentage = HSC_Percentage;
        this.SSC_Percentage = SSC_Percentage;
        this.PCM_Percentage = PCM_Percentage;
        this.State_merit_rank = state_merit_rank;
        this.CET_percentile = CET_percentile;
        this.First_preference = first_preference;
        this.Second_preference = second_preference;
        this.Third_preference = third_preference;
        this.Forth_preference = forth_preference;
        this.Fifth_preference = fifth_preference;
        this.Sixth_preference = sixth_preference;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public void setFirst_preference(String first_preference) {
        First_preference = first_preference;
    }

    public void setSecond_preference(String second_preference) {
        Second_preference = second_preference;
    }

    public void setThird_preference(String third_preference) {
        Third_preference = third_preference;
    }

    public void setForth_preference(String forth_preference) {
        Forth_preference = forth_preference;
    }

    public void setFifth_preference(String fifth_preference) {
        Fifth_preference = fifth_preference;
    }

    public void setSixth_preference(String sixth_preference) {
        Sixth_preference = sixth_preference;
    }

    public String getFirst_preference() {
        return First_preference;
    }

    public String getSecond_preference() {
        return Second_preference;
    }

    public String getThird_preference() {
        return Third_preference;
    }

    public String getForth_preference() {
        return Forth_preference;
    }

    public String getFifth_preference() {
        return Fifth_preference;
    }

    public String getSixth_preference() {
        return Sixth_preference;
    }

    public String getName() {
        return Name;
    }

    public String getDOB() {
        return DOB;
    }

    public String getGender() {
        return Gender;
    }

    public String getCaste() {
        return Caste;
    }

    public String getEWS() {
        return EWS;
    }

    public String getPWD() {
        return PWD;
    }

    public String getHU() {
        return HU;
    }

    public String getOrphan() {
        return Orphan;
    }

    public String getDefense() {
        return Defense;
    }

    public String getHSC_Percentage() {
        return HSC_Percentage;
    }

    public String getSSC_Percentage() {
        return SSC_Percentage;
    }

    public String getPCM_Percentage() {
        return PCM_Percentage;
    }

    public String getState_merit_rank() {
        return State_merit_rank;
    }

    public String getCET_percentile() {
        return CET_percentile;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setCaste(String caste) {
        Caste = caste;
    }

    public void setEWS(String EWS) {
        this.EWS = EWS;
    }

    public void setPWD(String PWD) {
        this.PWD = PWD;
    }

    public void setHU(String HU) {
        this.HU = HU;
    }

    public void setOrphan(String orphan) {
        Orphan = orphan;
    }

    public void setDefense(String defense) {
        Defense = defense;
    }

    public void setHSC_Percentage(String HSC_Percentage) {
        this.HSC_Percentage = HSC_Percentage;
    }

    public void setSSC_Percentage(String SSC_Percentage) {
        this.SSC_Percentage = SSC_Percentage;
    }

    public void setPCM_Percentage(String PCM_Percentage) {
        this.PCM_Percentage = PCM_Percentage;
    }

    public void setState_merit_rank(String state_merit_rank) {
        State_merit_rank = state_merit_rank;
    }

    public void setCET_percentile(String CET_percentile) {
        this.CET_percentile = CET_percentile;
    }
}
