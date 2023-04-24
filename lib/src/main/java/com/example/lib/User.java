package com.example.lib;



public class User {
    private String telegramHandle;
    private String uid;
    public String getTelegramHandle() {
        return telegramHandle;
    }
    public String getUid() {
        return uid;
    }


    public User(String telegramHandle, String uid) {
        this.telegramHandle = telegramHandle;
        this.uid = uid;
    }

    public User(){
    }



}

