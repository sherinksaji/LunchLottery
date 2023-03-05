package com.example.lib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Entry {
    /**
     * Entry takes in inputs Telehandle and a Gregorian Calendar
     * This object is used to collect user input
     * This object will be written into firebase database
     * This object will be read back into the app
     * This object will be sorted according to a comparator
     * Sort according to what? Telehandle?
     *
     */

    private String telegramHandle;
    private long bookingTimeStamp;// the time that the user booked for

    public Entry(String telegramHandle, GregorianCalendar gregCal) {
        this.telegramHandle = telegramHandle;
        this.bookingTimeStamp = gregCal.getTime().getTime();
    }
    public Entry(GregorianCalendar gregCal){
        this.telegramHandle = "No match";
        this.bookingTimeStamp= gregCal.getTime().getTime();
    }

    public String getTelegramHandle() {
        return this.telegramHandle;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTimeString = dateFormat.format(this.getGregCal().getTime());
        return "Timing: "+dateTimeString;
    }

    @Override
    public String toString() {
        if (getTelegramHandle()=="No match"){
            return "ENTRY: \n" +
                    "TelegramHandle : " + "This is an empty entry object"+ '\n' +
                    this.calStr() + '\n';
        }
        else {
            return "ENTRY: \n" +
                    "TelegramHandle : " + "@" + this.getTelegramHandle() + '\n' +
                    this.calStr() + '\n';
        }
    }

    public String displayResultToMatch(){
        if (getTelegramHandle()=="No match"){
            return "Results: \n" +
                    "Sorry, for this "+this.calStr()+","+ '\n' +
                    "you have no match"+ '\n';
        }
        else{
            return "Results: \n" +
                    "TelegramHandle : " + "@" + this.getTelegramHandle() + '\n' +
                    this.calStr() + '\n';
        }
    }
}