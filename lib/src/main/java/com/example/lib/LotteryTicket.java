package com.example.lib;

public class LotteryTicket implements Identifiable,Slottable{

    private String telegramHandle;
    private String calStr;


    public String getTelegramHandle() {
        return telegramHandle;
    }

    public void setTelegramHandle(String telegramHandle) {
        this.telegramHandle = telegramHandle;
    }

    public String getCalStr() {
        return calStr;
    }

    public void setCalStr(String calStr) {
        this.calStr = calStr;
    }

    public LotteryTicket(String telegramHandle, String calStr) {
        this.telegramHandle = telegramHandle;
        this.calStr = calStr;
    }

    public LotteryTicket(){
        this.telegramHandle="telegramHandle";
        this.calStr="dd-MM-yyyy HH:MM";
    }

    @Override
    public String identity() {
        return getTelegramHandle();
    }

    @Override
    public String timeSlot() {return getCalStr();}
}
