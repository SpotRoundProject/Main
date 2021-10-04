package com.example.spotround;

public class User
{
    String Username,password;
    String phone;
    String Aplicationid;
    String ayfw;

    public User(String username, String password)
    {
        this.Username = username;
        this.password = password;
    }
   public User()
   {
       this.Username="";
       this.password="";
       this.phone="";
       this.Aplicationid="";
       this.ayfw="";
   }

    public User(String username, String password, String phone) {
        Username = username;
        this.password = password;
        this.phone = phone;
    }

    public User(String username, String password, String phone, String aplicationid, String ayfw) {
        Username = username;
        this.password = password;
        this.phone = phone;
        Aplicationid = aplicationid;
        this.ayfw = ayfw;
    }

    public String getAplicationid() {
        return Aplicationid;
    }

    public String getAyfw() {
        return ayfw;
    }

    public void setAplicationid(String aplicationid) {
        Aplicationid = aplicationid;
    }

    public void setAyfw(String ayfw) {
        this.ayfw = ayfw;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
