package com.example.lib;

import java.util.GregorianCalendar;

public class Ticket {
    String telegramHandle;
    long bookingTimeStamp;
    String calStr;

    public String getTelegramHandle() {
        return telegramHandle;
    }

    public void setTelegramHandle(String telegramHandle) {
        this.telegramHandle = telegramHandle;
    }

    public long getBookingTimeStamp() {
        return bookingTimeStamp;
    }

    public void setBookingTimeStamp(long bookingTimeStamp) {
        this.bookingTimeStamp = bookingTimeStamp;
    }

    public String getCalStr() {
        return calStr;
    }

    public void setCalStr(String calStr) {
        this.calStr = calStr;
    }

    public Ticket(String telegramHandle, long bookingTimeStamp, String calStr) {
        this.telegramHandle = telegramHandle;
        this.bookingTimeStamp = bookingTimeStamp;
        this.calStr = calStr;
    }

    public Ticket(){
        this("telegramHandle",123,"10-12-2012");
    }
}
