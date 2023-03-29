package com.example.lib;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Entry {




    private String telegramHandle;
    private long bookingTimeStamp;// the time that the user booked for

    public Entry(String telegramHandle, GregorianCalendar gregCal) {
        this.telegramHandle = telegramHandle;
        this.bookingTimeStamp = gregCal.getTime().getTime();
    }
    public Entry(GregorianCalendar gregCal){
        this("No match",gregCal);
    }

    public Entry(){
        this("telegramHandle",new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault()));
    }

    public Entry(Ticket ticket){
        GregorianCalendar gregCal=new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        gregCal.setTimeInMillis(ticket.getBookingTimeStamp());
        this.telegramHandle= ticket.getTelegramHandle();
        this.bookingTimeStamp=gregCal.getTime().getTime();
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
        GregorianCalendar gregCal = new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault());
        gregCal.setTimeInMillis(this.bookingTimeStamp);
        return gregCal;
    }

    public String calStr (){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateFormat.setTimeZone(TimeZone.getDefault());
        String dateTimeString = dateFormat.format(this.getGregCal().getTime());
        return dateTimeString;
    }

    public Ticket fbTicket(){
        return new Ticket(this.getTelegramHandle(),this.getBookingTimeStamp(),this.calStr());
    }



    @Override
    public String toString() {
        if (getTelegramHandle()=="No match"){
            return "ENTRY: \n" +
                    "TelegramHandle : " + "This is an empty entry object"+ '\n' +
                    "Timing : "+this.calStr() + '\n';
        }
        else {
            return "ENTRY: \n" +
                    "TelegramHandle : " + "@" + this.getTelegramHandle() + '\n' +
                    "Timing : "+this.calStr() + '\n';
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
                    this.calStr() + '\n'; //why need to have gettelegramhandle
        }
    }
}
