package com.example.lib;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Week {
    /**

     The underlying logic of the Week Class:


     Our results are shown on a weekly basis
     users can enter lottery on a weekly basis
        -Today is 8/3/2023
        -Today falls in the 10th week of the entire year (Gregorian calendar)
        -user can enter lottery for Week 11.
        -user can see results for Week 10, if they had entered the lottery for Week10 during Week9

     -Construct a default GregorianCalendar using the current time. There are a few ways to do this,
     research and pick the appropriate one.

     -Think about what happens when it is week 1 of the year, are we going to be able to show the results of week1?
     -Think about the last week of the year, are we going to let user enter the lottery for the next year's first week




     https://docs.oracle.com/javase/7/docs/api/java/util/GregorianCalendar.html

     */


    public static String weekForViewResult (){
        // returns the week for viewing the results (e.g. Week12)
        GregorianCalendar calendar = new GregorianCalendar();
        int this_week = calendar.get(Calendar.WEEK_OF_YEAR);
        String s = String.valueOf(this_week);
        return "Week" + s;
    }

    public static String weekForJoinLottery (){
        // returns the week the user can enter the lottery (e.g. Week13)
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        int next_week = calendar.get(Calendar.WEEK_OF_YEAR);
        String s = String.valueOf(next_week);
        return "Week" + s;
    }

    /**
     *needed Week Method: public int minDayForJoinLottery ()
     *needed Week Method: public int minMonthForJoinLottery ()
     *needed Week Method: public int minYearForJoinLottery ()
     * implement methods based on current Gregorian calendar time
     * We use Gregorian Calendar for the rest of the app so pls use it
     all these methods will give me the min date to allow user to select on
     date picker
     */

    // return the day for Monday of the next week
    public static int minDayForJoinLottery (){
        // construct a default Gregorian Calendar
        GregorianCalendar calendar = new GregorianCalendar();
        // Add one week to this date
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        // set the day of the week to Monday
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // return the date for Monday of this ( the next ) week
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    // return the month of the Monday of the next week
    public static int minMonthForJoinLottery(){
        // construct a default Gregorian Calendar
        GregorianCalendar calendar = new GregorianCalendar();
        // add one week to this date
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        // set the day of the week to Monday
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        // return the month of this week's Monday
        return calendar.get(Calendar.MONTH) + 1;
    }

    // return the year of the Monday of next week
    public static int minYearForJoinLottery(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.get(Calendar.YEAR);
    }

    /**
     *needed Week Method: public int maxDayForJoinLottery ()
     *needed Week Method: public int maxMonthForJoinLottery ()
     *needed Week Method: public int maxYearForJoinLottery ()
     * implement methods based on current Gregorian calendar time
     * We use Gregorian Calendar for the rest of the app so pls use it
     all these methods will give me the max date to allow user to select on
     date picker
     */

    public static int maxDayForJoinLottery (){
        // construct a default Gregorian Calendar
        GregorianCalendar calendar = new GregorianCalendar();
        // Add one week to this date
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        // set the day of the week to Friday
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        // return the date for Friday of the next week
        return calendar.get(Calendar.DAY_OF_MONTH);
        // return 0;
    }

    public static int maxMonthForJoinLottery(){
        // construct a default Gregorian Calendar
        GregorianCalendar calendar = new GregorianCalendar();
        // add one week to this date
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        // set the day of the week to Friday
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        // return the month of this week's Friday
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int maxYearForJoinLottery(){
        // construct a new Gregorian Calendar
        GregorianCalendar calendar = new GregorianCalendar();
        // add one week to this date
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        // set the day of the week to Friday
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        // return the year of this week's Friday
        return calendar.get(Calendar.YEAR);
    }

    //public static void main(String[] args) {
      //  System.out.println("The Monday of the next week (should be 3): " + minDayForJoinLottery());
        //System.out.println("The month of the Monday of next week ( should be 4): " + minMonthForJoinLottery());
        //System.out.println("The year of the Monday of the next week (should be 2023): " + minYearForJoinLottery());

        //System.out.println("The Friday of the next week (should be 7): " + maxDayForJoinLottery());
        //System.out.println("The month of the Friday of next week ( should be 4): " + maxMonthForJoinLottery());
        //System.out.println("The year of the Friday of the next week (should be 2023): " + maxYearForJoinLottery());

        //System.out.println("You can view results for: " + weekForViewResult());
        //System.out.println("You can enter the lottery for: " + weekForJoinLottery());
    //}




}

