


package com.example.lib;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

interface Ticketable {
    Ticket fbTicket();
}

public class LotteryEntry implements Ticketable{

    private String telegramHandle;
    private long bookingTimeStamp;// the time that the user booked for

    public LotteryEntry(String telegramHandle, GregorianCalendar gregCal) {
        this.telegramHandle = telegramHandle;
        this.bookingTimeStamp = gregCal.getTime().getTime();
    }
    public LotteryEntry(GregorianCalendar gregCal){
        this("No match",gregCal);
    }

    public LotteryEntry(){
        this("telegramHandle",new GregorianCalendar(TimeZone.getDefault(), Locale.getDefault()));
    }

    public LotteryEntry(Ticket ticket){
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm",Locale.getDefault());
        return dateFormat.format(this.getGregCal().getTime());

    }


    @Override
    public Ticket fbTicket(){
        return new Ticket(this.getTelegramHandle(),this.getBookingTimeStamp(),this.calStr());
    }



    @Override
    public String toString() {
        if (getTelegramHandle().equals("No match")){
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



}
