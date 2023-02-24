package com.example.androidapp;

public class User {
    private String telegramHandle;
    private String email;
    private String uid;

    public String getTelegramHandle() {
        return telegramHandle;
    }

    public void setTelegramHandle(String telegramHandle) {
        this.telegramHandle = telegramHandle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public User(String telegramHandle, String email, String uid) {
        this.telegramHandle = telegramHandle;
        this.email = email;
        this.uid = uid;
    }







    public User(){
    }



}