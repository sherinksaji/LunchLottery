package com.example.lib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Entry {
    /**
     * Entry takes in inputs Telehandle, lunchStartTime,
     * and Date
     * This object is used to collect user input
     * This object will be written into firebase database
     * This object will be read back into the app
     * This object will be sorted according to a comparator
     * Sort according to what? Telehandle?
     *
     */

    String telegramHandle;
    long bookingTimeStamp;// the time that the user booked for

    public Entry(String telegramHandle, GregorianCalendar gregCal) {
        this.telegramHandle = telegramHandle;
        this.bookingTimeStamp = gregCal.getTime().getTime();
    }

    public String getTelegramHandle() {
        return telegramHandle;
    }

    public void setTelegramHandle(String telegramHandle) {
        this.telegramHandle = telegramHandle;
    }

    private long getBookingTimeStamp() {
        return this.bookingTimeStamp;
    }

    private void setBookingTimeStamp(GregorianCalendar gregCal) {
        this.bookingTimeStamp = gregCal.getTime().getTime();
    }

    private GregorianCalendar getGregCal(){
        GregorianCalendar gregCal = new GregorianCalendar();
        gregCal.setTimeInMillis(this.bookingTimeStamp);
        return gregCal;
    }

    public String calStr (){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(this.getGregCal());
    }

    @Override
    public String toString() {
        return "ENTRY: \n" +
                "TelegramHandle:" + this.getTelegramHandle() + '\n' +
                "Timing:" + this.calStr() + '\n';
    }
}
