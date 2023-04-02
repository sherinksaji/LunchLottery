package com.example.lib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

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

    // interface for getting the week title
    public interface WeekTitle {
        public String getWeekTitle();
    }

    // Class that implements the WeekTitle interface
    public static class CurrentWeek implements WeekTitle {
        @Override
        public String getWeekTitle() {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            int this_week = calendar.get(Calendar.WEEK_OF_YEAR);
            String s = String.valueOf(this_week);
            return "Week" + s;
        }
    }

    public static class NextWeek implements WeekTitle {
        @Override
        public String getWeekTitle() {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            int next_week = calendar.get(Calendar.WEEK_OF_YEAR);
            String s = String.valueOf(next_week);
            return "Week" + s;
        }
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

    public interface day{
        public int getDay();
        public int getMonth();
        public int getYear();
    }

    public static class Weekend{
        public static boolean isWeekend() {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            if (calendar.get(Calendar.DAY_OF_WEEK) == (Calendar.SATURDAY) || calendar.get(Calendar.DAY_OF_WEEK) == (Calendar.SUNDAY)) {
                return true;
            }
            else{
                return false;
            }
        }
    }

    public static class calStrCreator{
        public static String getCalStr(GregorianCalendar calendar){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            return sdf.format(calendar.getTime());

        }
    }
    //from chatGPT

    // Interface for getting the min day for joining the lottery (Monday of next week)
    public interface MinDayForJoinLottery {
        public int getMinDay();
        public int getMinMonth();
        public int getMinYear();
    }

    // Class that implements MinDayForJoinLottery interface
    public static class MinDateForJoinLottery implements MinDayForJoinLottery {
        @Override
        public int getMinDay() {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        @Override
        public int getMinMonth() {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            return calendar.get(Calendar.MONTH) + 1;
        }

        @Override
        public int getMinYear() {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            return calendar.get(Calendar.YEAR);
        }
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

    // Interface for getting max date for joining lottery (Friday of next week)
    public interface MaxDayForJoinLottery {
        public int getMaxDay();
        public int getMaxMonth();
        public int getMaxYear();
    }

    // Class that implements MaxDayForJoinLottery interface
    public static class MaxDateForJoinLottery implements MaxDayForJoinLottery {
        @Override
        public int getMaxDay() {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            return calendar.get(Calendar.DAY_OF_MONTH);
        }

        @Override
        public int getMaxMonth() {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            return calendar.get(Calendar.MONTH) + 1;
        }

        @Override
        public int getMaxYear() {
            GregorianCalendar calendar = new GregorianCalendar(Locale.getDefault());
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
            return calendar.get(Calendar.YEAR);
        }
    }

    public static void main(String[] args) {
        Week.MinDayForJoinLottery current_week = new Week.MinDateForJoinLottery();
        int minDay = current_week.getMinDay();
        System.out.println("The Monday of the next week (should be 3): " + minDay);
        //System.out.println("The month of the Monday of next week ( should be 4): " + minMonthForJoinLottery());
        //System.out.println("The year of the Monday of the next week (should be 2023): " + minYearForJoinLottery());

        //System.out.println("The Friday of the next week (should be 7): " + maxDayForJoinLottery());
        //System.out.println("The month of the Friday of next week ( should be 4): " + maxMonthForJoinLottery());
        //System.out.println("The year of the Friday of the next week (should be 2023): " + maxYearForJoinLottery());

        //System.out.println("You can view results for: " + weekForViewResult());
        //System.out.println("You can enter the lottery for: " + weekForJoinLottery());
    }




}

