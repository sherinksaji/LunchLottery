package com.example.lib;

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

    public String weekForViewResult (){
        //notice there is no space between Week and 10
        return "Week10";
    }

    public String weekForJoinLottery (){
        //notice there is no space between Week and 11
        return "Week11";
    }

    /**
     TODO 1 Implement these methods for minDate
     *needed Week Method: public int minDayForJoinLottery ()
     *needed Week Method: public int minMonthForJoinLottery ()
     *needed Week Method: public int minYearForJoinLottery ()
     * implement methods based on current Gregorian calendar time
     * We use Gregorian Calendar for the rest of the app so pls use it
     all these methods will give me the min date to allow user to select on
     date picker
     */
    public int minDayForJoinLottery (){
        return 0;
    }
    public int minMonthForJoinLottery(){
        //if month is April, return 4
        return 0;
    }

    public int minYearForJoinLottery(){
        return 0;
    }

    /**
     TODO 2 Implement these methods for maxDate
     *needed Week Method: public int maxDayForJoinLottery ()
     *needed Week Method: public int maxMonthForJoinLottery ()
     *needed Week Method: public int maxYearForJoinLottery ()
     * implement methods based on current Gregorian calendar time
     * We use Gregorian Calendar for the rest of the app so pls use it
     all these methods will give me the max date to allow user to select on
     date picker
     */

    public int maxDayForJoinLottery (){
        return 0;
    }
    public int maxMonthForJoinLottery(){
        //if month is April, return 4
        return 0;
    }

    public int maxYearForJoinLottery(){
        return 0;
    }

    /**
     TODO 3 Help test out end of year and beginning of year scenario in the date picker of the app
     */


}
